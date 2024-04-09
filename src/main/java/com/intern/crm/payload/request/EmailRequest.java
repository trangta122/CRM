package com.intern.crm.payload.request;

public class EmailRequest {
    private String recipient;
    private String subject;
    private String message;
    private String attachment;

    public EmailRequest() {
    }

    public EmailRequest(String recipient, String subject, String message, String attachment) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
