package com.example.adapter.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class FineResponse {
    private String vehicleCertificate;
    private String taxPayerID;
    private BigDecimal accruedAmount;
    private BigDecimal fineAmount;
    private String resolution;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date resolutionDate;
    private String article;
}
