package com.intern.crm.service.impl;

import com.intern.crm.entity.Activity;
import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.TemplateFile;
import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.TemplateFileRepository;
import com.intern.crm.service.EmailService;
import freemarker.core.ParseException;
import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateFileRepository fileRepository;
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    private Configuration configFreemarker;
    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleEmail(EmailRequest emailRequest) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailRequest.getRecipient());
            mailMessage.setSubject(emailRequest.getSubject());
            mailMessage.setText(emailRequest.getMessage());

            javaMailSender.send(mailMessage);
            return "Mail sent successfully...";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String sendEmailWithAttachment(EmailRequest emailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailRequest.getRecipient());
            mimeMessageHelper.setSubject(emailRequest.getSubject());
            mimeMessageHelper.setText(emailRequest.getMessage());

            TemplateFile attachment = fileRepository.findById(emailRequest.getAttachment()).get();
            String path = "uploads/" + attachment.getPhysicalPath();

            FileSystemResource file = new FileSystemResource(new File(path));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSender.send(mimeMessage);

            return "Mail sent successfully...";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendColdEmail(EmailRequest emailRequest, Map<String, Object> model) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = configFreemarker.getTemplate("cold-email.html");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(sender);
            helper.setTo(emailRequest.getRecipient());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(content, true);

            String logoPath = "./static/logo.png";
            helper.addInline("logo.png", new ClassPathResource(logoPath));
            javaMailSender.send(mimeMessage);

            return "Cold email is being sent...";

        } catch (TemplateNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (MalformedTemplateNameException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendQuotation(String id, EmailRequest emailRequest) throws MessagingException, IOException {
        Opportunity opportunity = opportunityRepository.findById(id).get();

        //Merge data to docx
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());

        String inputFile = "uploads/quotation.docx";
        String outputFile = "uploads/" + currentDateTime + "quotation.docx";

        XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get(inputFile)));
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            for (XWPFRun run : paragraph.getRuns()) {
                String docText = run.getText(0);
                if  (docText != null) {
                    docText = docText.replace("${date}", dateFormat(emailRequest.getExpiration()));
                    docText = docText.replace("${company}", opportunity.getCompany());
                    docText = docText.replace("${email}", opportunity.getEmail());
                    docText = docText.replace("${address}", opportunity.getAddress());
                    docText = docText.replace("${product}", emailRequest.getProduct());
                    docText = docText.replace("${description}", emailRequest.getDescription());
                    docText = docText.replace("${price}", numberFormat(emailRequest.getPrice()));
                    docText = docText.replace("${tax}", numberFormat(emailRequest.getTax()));
                    docText = docText.replace("${amount}", numberFormat(emailRequest.getPrice()));
                    docText = docText.replace("${VAT}", numberFormat(emailRequest.getVat()));
                    docText = docText.replace("${total}", numberFormat(emailRequest.getTotal()));
                    docText = docText.replace("${condition}", emailRequest.getCondition());
                    docText = docText.replace("${fullname}", opportunity.getSalesperson().getFullname());
                    docText = docText.replace("${contact}", opportunity.getSalesperson().getEmail());
                }
                run.setText(docText, 0);
            }
        }

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        document.write(outputStream);

        //save output docx file information on database
        TemplateFile file = new TemplateFile(currentDateTime + "quotation", "docx", "/"+outputFile, true);
        file.setOpportunity(opportunity);
        fileRepository.save(file);

        //Send email attach quoatation file
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        Map<String, String> data = new HashMap<>();
        data.put("salesperson", opportunity.getSalesperson().getFullname());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template template = configFreemarker.getTemplate("quotation.html");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

            helper.setFrom(sender);
            helper.setTo(emailRequest.getRecipient());
            helper.setSubject("Quotation from Blossom");
            helper.setText(content, true);

            FileSystemResource resource = new FileSystemResource(new File(outputFile));
            helper.addAttachment(resource.getFilename(), resource);
            javaMailSender.send(mimeMessage);

            //Log send quotation activity
            String detail = "Quotation sent: " + file.getName();
            Activity activity = new Activity("Auto-log", detail, new Date(), false);

            return "Quotaion email is being sent...";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public String numberFormat(double number) {
        return new DecimalFormat("0").format(number);
    }

    public String dateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(date);
    }
}
