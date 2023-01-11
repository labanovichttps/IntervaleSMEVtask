package com.example.smev.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class FineRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vehicleCertificate;
    private String taxPayerID;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
