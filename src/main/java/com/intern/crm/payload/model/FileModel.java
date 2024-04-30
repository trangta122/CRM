package com.intern.crm.payload.model;

public class FileModel {
    private String id;
    private String name;
    private String type;
    private String physicalPath;
    private boolean is_template;

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

    public boolean isIs_template() {
        return is_template;
    }

    public void setIs_template(boolean is_template) {
        this.is_template = is_template;
    }
}
