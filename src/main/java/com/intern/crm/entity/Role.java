package com.intern.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ERole name;


    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }
    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
