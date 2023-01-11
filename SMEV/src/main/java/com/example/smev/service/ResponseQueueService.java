package com.example.smev.service;

import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseQueueService {
    public ResponseEntity<FineResponse> getFineResponse(FineRequest fineRequest);
    public ResponseEntity<HttpStatus> deleteResponseFromQueue(FineResponse fineResponse);
}
