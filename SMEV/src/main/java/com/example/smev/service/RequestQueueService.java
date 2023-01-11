package com.example.smev.service;

import com.example.smev.dao.FineRequest;
import org.springframework.http.ResponseEntity;

public interface RequestQueueService {

    ResponseEntity<FineRequest> saveFineRequestToQueue(FineRequest fineRequest) throws InterruptedException;

    FineRequest getFineFromQueue(FineRequest fineRequest);
    void deleteRequestFromQueue(FineRequest fineRequest);
}
