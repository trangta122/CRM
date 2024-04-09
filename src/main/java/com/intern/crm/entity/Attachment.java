package com.intern.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "files")
@EntityListeners(EntityListeners.class)
public class Attachment extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String type;
    private String physicalPath;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    }, mappedBy = "files")
    @JsonIgnore
    private List<Opportunity> opportunities = new ArrayList<>();

    //constructor
    public Attachment() {
    }

    public Attachment(String name, String type, String physicalPath) {
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

    public List<Opportunity> getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(List<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }
}
