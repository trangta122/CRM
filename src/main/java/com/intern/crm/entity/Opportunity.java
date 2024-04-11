package com.intern.crm.entity;

import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "opportunities")
@EntityListeners(AuditingEntityListener.class)
public class Opportunity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name; //opportunity name
    private String company;
    private String email;
    private String phone;
    private String address;
    private String website;
    private String description;
    private Double revenue; //expected revenue
    private EPriority priority;
    private Float probability;
    private Boolean isCustomer = false;
    private String lostReason;
    private String lostNote;


    @ManyToOne(fetch = FetchType.LAZY)
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    private User salesperson;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "opportunity_contacts",
    joinColumns = { @JoinColumn(name = "opportunity_Id")},
    inverseJoinColumns = {@JoinColumn(name = "contact_Id")})
    private List<Contact> contacts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "opportunity_files",
                joinColumns = {@JoinColumn(name = "opportunity_id")},
                inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private List<Attachment> files = new ArrayList<>();

    public void addContact(Contact contact) {
        this.contacts.add(contact);
        contact.getOpportunities().add(this);
    }

    public void addFile(Attachment file) {
        this.files.add(file);
        file.getOpportunities().add(this);
    }

    public Opportunity() {
    }

    public Opportunity(String id, String name, String company, String email, String phone, String address, String website, String description, Double revenue, EPriority priority, Float probability, Boolean isCustomer, String lostReason, String lostNote) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.website = website;
        this.description = description;
        this.revenue = revenue;
        this.priority = priority;
        this.probability = probability;
        this.isCustomer = isCustomer;
        this.lostReason = lostReason;
        this.lostNote = lostNote;
    }

    //getter & setter
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(User salesperson) {
        this.salesperson = salesperson;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Attachment> getFiles() {
        return files;
    }

    public void setFiles(List<Attachment> files) {
        this.files = files;
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
