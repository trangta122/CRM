package com.intern.crm.payload.model;

import jakarta.validation.constraints.Email;

import java.util.Date;
import java.util.List;

public class ContactModel {
    private String id;
    private String firstname;
    private String lastname;
    private String fullname;
    @Email
    private String email;
    private String phone;
    private Date birthday;
    private String gender;
    private List<String> opportunityIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public void setFullname(String fullname) {
        this.fullname = getFirstname() + " " + getLastname();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getOpportunityIds() {
        return opportunityIds;
    }

    public void setOpportunityIds(List<String> opportunityIds) {
        this.opportunityIds = opportunityIds;
    }
}
