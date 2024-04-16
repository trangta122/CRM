package com.intern.crm.controller;

import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jdk.jfr.consumer.RecordedMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@Tag(name = "Send Email")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/mails")
public class EmailController {
    @Autowired
    EmailService emailService;

    @Operation(summary = "Send a simple email")
    @PostMapping("")
    public String sendEmail(@RequestBody EmailRequest request) {
        return emailService.sendSimpleEmail(request);
    }

    @Operation(summary = "Send an email with an attachment")
    @PostMapping("/attachment")
    public String sendEmailWithAttachment(@RequestBody EmailRequest request) {
        return emailService.sendEmailWithAttachment(request);
    }

    @Operation(summary = "Send email with template", description = "Send cold email")
    @PostMapping("/template")
    public String sendTemplateMail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("company", emailRequest.getCompany());
        model.put("salesperson", emailRequest.getSalesperson());
        model.put("description", emailRequest.getDescription());
        return emailService.sendColdEmail(emailRequest, model);
    }
}
