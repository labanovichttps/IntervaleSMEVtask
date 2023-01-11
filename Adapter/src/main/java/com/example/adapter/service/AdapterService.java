package com.example.adapter.service;

import com.example.adapter.dao.FineRequest;
import com.example.adapter.dao.FineResponse;
import org.springframework.http.ResponseEntity;

public interface AdapterService {
    ResponseEntity<FineResponse> requestFineFromSMEV(FineRequest fineRequest);

}
