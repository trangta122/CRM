package com.intern.crm.service.impl;

import com.intern.crm.entity.TemplateFile;
import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.repository.TemplateFileRepository;
import com.intern.crm.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateFileRepository fileRepository;
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

            FileSystemResource file = new FileSystemResource(new java.io.File(path));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSender.send(mimeMessage);

            return "Mail sent successfully...";

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
