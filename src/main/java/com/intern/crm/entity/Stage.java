package com.intern.crm.entity;

import com.intern.crm.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "stages")
@EntityListeners(AuditingEntityListener.class)
public class Stage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code = String.valueOf(UUID.randomUUID());

    @NotBlank
    private String name;
    @NotNull
    private Double revenue; //total expected revenue group by StageID

    //constructor
    public Stage() {
    }

    public Stage(String name, Double revenue) {
        this.name = name;
        this.revenue = revenue;
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

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
}
