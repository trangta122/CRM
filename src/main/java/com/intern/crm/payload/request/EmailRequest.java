package com.intern.crm.payload.request;

import java.util.Date;

public class EmailRequest {
    private String recipient;
    private String subject;
    private String message;
    private String attachment;

    //Cold email request
    private String company; //customer in quotation
    private String salesperson;
    private String description;

    //Quotation request
    private Date expiration;
    private String product;
    private Double price;
    private int tax;
    private Double total;
    private String condition;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total*(1 + tax/100);
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
