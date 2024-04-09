package com.intern.crm.service;

import com.intern.crm.payload.request.EmailRequest;

public interface EmailService {
    //Send simple email
    String sendSimpleEmail(EmailRequest emailRequest);

    //Send email with an attachment
    String sendEmailWithAttachment(EmailRequest emailRequest);
}
