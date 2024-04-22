package com.intern.crm.payload.model;

import com.intern.crm.entity.EPriority;

public class OpportunityModel {
    private String id;
    private String name;
    private String company;
    private String email;
    private String phone;
    private String address;
    private String website;
    private String description;
    private Double revenue;
    private Boolean isCustomer;
    private EPriority priority;
    private Float probability;
    private String lostReason;
    private String lostNote;
    private StageModel stage;
    private UserModel salesperson;
    private ContactModel contacts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Boolean getCustomer() {
        return isCustomer;
    }

    public void setCustomer(Boolean customer) {
        isCustomer = customer;
    }

    public StageModel getStage() {
        return stage;
    }

    public void setStage(StageModel stage) {
        this.stage = stage;
    }

    public UserModel getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(UserModel salesperson) {
        this.salesperson = salesperson;
    }

    public ContactModel getContacts() {
        return contacts;
    }

    public void setContacts(ContactModel contacts) {
        this.contacts = contacts;
    }

    public EPriority getPriority() {
        return priority;
    }

    public void setPriority(EPriority priority) {
        this.priority = priority;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public String getLostReason() {
        return lostReason;
    }

    public void setLostReason(String lostReason) {
        this.lostReason = lostReason;
    }

    public String getLostNote() {
        return lostNote;
    }

    public void setLostNote(String lostNote) {
        this.lostNote = lostNote;
    }
}
