package com.intern.crm.controller;

import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.consumer.RecordedMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Tag(name = "Send Email")
@RestController
@RequestMapping("/mail")
public class EmailController {
    @Autowired
    EmailService emailService;
    @PostMapping("")
    public String sendEmail(@RequestBody EmailRequest request) {
        return emailService.sendSimpleEmail(request);
    }

    @PostMapping("/attachment")
    public String sendEmailWithAttachment(@RequestBody EmailRequest request) {
        return emailService.sendEmailWithAttachment(request);
    }
}
