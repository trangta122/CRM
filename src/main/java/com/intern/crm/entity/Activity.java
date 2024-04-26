package com.intern.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "activities")
@EntityListeners(EntityListeners.class)
public class Activity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String type;
    private String detail;
    private Date date;
    private boolean isDone = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id")
    @JsonIgnore
    private Opportunity opportunity;

    public Activity() {
    }

    public Activity(String type, String detail, Date date, boolean isDone) {
        this.type = type;
        this.detail = detail;
        this.date = date;
        this.isDone = isDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }
}
