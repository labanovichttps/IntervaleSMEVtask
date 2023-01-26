package com.example.smev.service;

import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResponseQueueService {
    public ResponseEntity<List<FineResponse>> getFineResponse(FineRequest fineRequest);
    public ResponseEntity<HttpStatus> deleteResponseFromQueue(List<FineResponse> fineResponse);
}
