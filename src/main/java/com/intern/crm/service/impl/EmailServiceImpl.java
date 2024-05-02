package com.intern.crm.service.impl;

import com.intern.crm.entity.Activity;
import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.TemplateFile;
import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.TemplateFileRepository;
import com.intern.crm.service.EmailService;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
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
import java.nio.file.Path;
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
    public String sendEmailWithAttachment(EmailRequest emailRequest) throws IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailRequest.getRecipient());
            mimeMessageHelper.setSubject(emailRequest.getSubject());
            mimeMessageHelper.setText(emailRequest.getMessage());

            TemplateFile attachment = fileRepository.findById(emailRequest.getAttachment()).get();
            String path = attachment.getPhysicalPath();

            FileSystemResource file = new FileSystemResource(new File(path));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSender.send(mimeMessage);

            return "Mail sent successfully...";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        finally {
            Path path = Paths.get(fileRepository.findById(emailRequest.getAttachment()).get().getPhysicalPath());
            Files.delete(path);
            fileRepository.deleteById(emailRequest.getAttachment());
        }
    }


    @Override
    public String sendColdEmail(String templateId, String opportunityId, EmailRequest emailRequest) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Map<String, String> data = new HashMap<>();
        data.put("company", emailRequest.getCompany());
        data.put("salesperson", emailRequest.getSalesperson());
        data.put("description", emailRequest.getDescription());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = configFreemarker.getTemplate("cold-email.html");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

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

        String inputFile = "uploads/20240502153332241_quotation.docx";
        String outputFile = currentDateTime + "quotation.pdf";
        String outputFilePath = "uploads/" + outputFile;

        String [] fieldNames = new String[] {
                "date",
                "company",
                "email",
                "address",
                "product",
                "description",
                "price",
                "tax",
                "amount",
                "VAT",
                "total",
                "condition",
                "fullname",
                "contact"
        };

        String [] fieldValues = new String[] {
                dateFormat(emailRequest.getExpiration()),
                opportunity.getCompany(),
                opportunity.getEmail(),
                opportunity.getAddress(),
                emailRequest.getProduct(),
                emailRequest.getDescription(),
                numberFormat(emailRequest.getPrice()),
                numberFormat(emailRequest.getTax()),
                numberFormat(emailRequest.getPrice()),
                numberFormat(emailRequest.getVat()),
                numberFormat(emailRequest.getTotal()),
                emailRequest.getCondition(),
                opportunity.getSalesperson().getFullname(),
                opportunity.getSalesperson().getEmail()
        };

        Document document = new Document();
        document.loadFromFile(inputFile);
        document.getMailMerge().execute(fieldNames, fieldValues);
        document.saveToFile(outputFilePath, FileFormat.PDF);

        //save output docx file information on database
        TemplateFile file = new TemplateFile(outputFile, "PDF", outputFilePath, false);
        file.setOpportunity(opportunity);
        fileRepository.save(file);

        //Send email attach quotation file
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

            FileSystemResource resource = new FileSystemResource(new File(outputFilePath));
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
