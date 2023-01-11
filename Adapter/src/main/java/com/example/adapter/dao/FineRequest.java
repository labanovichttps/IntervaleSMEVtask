package com.example.adapter.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FineRequest {
    private String vehicleCertificate;
    private String taxPayerID;

    public FineRequest(@JsonProperty("vehicleCertificate") String vehicleCertificate,
                       @JsonProperty("taxPayerID") String taxPayerID) {
        this.vehicleCertificate = vehicleCertificate;
        this.taxPayerID = taxPayerID;
    }

    public FineRequest() {

    }
}
