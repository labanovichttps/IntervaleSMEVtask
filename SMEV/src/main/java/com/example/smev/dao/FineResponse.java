package com.example.smev.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@AllArgsConstructor
@Builder
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

    public FineResponse() {
    }

    public FineResponse(Fine fine) {
        this.taxPayerID = fine.getTaxPayerID();
        this.vehicleCertificate = fine.getVehicleCertificate();
        this. article = fine.getArticle();
        this.resolution = fine.getResolution();
        this.fineAmount = fine.getFineAmount();
        this.accruedAmount = fine.getAccruedAmount();
        this.resolutionDate = fine.getResolutionDate();
    }
}
