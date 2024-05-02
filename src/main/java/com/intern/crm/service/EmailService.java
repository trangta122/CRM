package com.intern.crm.service;

import com.intern.crm.payload.request.EmailRequest;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    //Send simple email
    String sendSimpleEmail(EmailRequest emailRequest);

    //Send email with an attachment
    String sendEmailWithAttachment(EmailRequest emailRequest) throws IOException;

    //Send email with template (cold email)
    String sendColdEmail(String templateId, String opportunityId, EmailRequest request) throws MessagingException;

    //Send quotation
    String sendQuotation(String id, EmailRequest request) throws MessagingException, IOException;
}
