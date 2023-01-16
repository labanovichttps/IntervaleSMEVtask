package com.example.adapter.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FineRequest {

    private String vehicleCertificate;
    private String taxPayerID;
}
