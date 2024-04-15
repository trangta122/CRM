package com.intern.crm.entity;

import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "template_files")
@EntityListeners(EntityListeners.class)
public class TemplateFile extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String type;
    private String physicalPath;
    private Boolean isFile;

    @OneToOne(fetch = FetchType.LAZY)
    private Opportunity opportunity;


    //constructor
    public TemplateFile() {
    }

    public TemplateFile(String name, String type, String physicalPath) {
        this.name = name;
        this.type = type;
        this.physicalPath = physicalPath;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }

    public Boolean getIsFile() {
        return isFile;
    }

    public void setIsFile(Boolean file) {
        isFile = file;
    }
}
