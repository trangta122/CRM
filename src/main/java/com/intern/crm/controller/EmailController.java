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

import java.io.IOException;
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
    public String sendEmailWithAttachment(@RequestBody EmailRequest request) throws IOException {
        return emailService.sendEmailWithAttachment(request);
    }

    @Operation(summary = "Send email with template", description = "Send cold email")
    @PostMapping("/template/{templateId}/opportunity/{opportunityId}")
    public String sendTemplateMail( @PathVariable("templateId") String id1,
                                    @PathVariable("opportunityId") String id2,
                                    @RequestBody EmailRequest emailRequest) throws MessagingException {
        return emailService.sendColdEmail(id1, id2, emailRequest);
    }

    @Operation(summary = "Send quotation")
    @PostMapping("/quotation/opportunity/{id}")
    public String sendQuotationMail(@PathVariable("id") String id,
                                    @RequestBody EmailRequest request) throws MessagingException, IOException {
        return emailService.sendQuotation(id, request);
    }
}
