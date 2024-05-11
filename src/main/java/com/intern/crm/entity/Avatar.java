package com.intern.crm.entity;

import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "avatars")
@EntityListeners(EntityListeners.class)
public class Avatar extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String type;
    private String physicalPath;

    public Avatar() {
    }

    public Avatar(String id, String name, String type, String physicalPath) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.physicalPath = physicalPath;
    }

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

}
