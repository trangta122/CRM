package com.intern.crm.entity;

import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "template_emails")
@Entity
@EntityListeners(EntityListeners.class)
public class TemplateEmail extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String type;
    private String name;
    private String physicalPath;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "opportunity_emails",
                joinColumns = {@JoinColumn(name = "opportunity_id")},
                inverseJoinColumns = {@JoinColumn(name = "template_emails_id")})
    private List<Opportunity> opportunities = new ArrayList<>();

    public TemplateEmail() {
    }

    public TemplateEmail(String type, String name, String physicalPath) {
        this.type = type;
        this.name = name;
        this.physicalPath = physicalPath;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

}
