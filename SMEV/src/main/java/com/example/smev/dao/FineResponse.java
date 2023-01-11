package com.example.smev.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
public class FineResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vehicleCertificate;
    private String taxPayerID;
    private BigDecimal accruedAmount;
    private BigDecimal fineAmount;
    private String resolution;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date resolutionDate;
    private String article;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
