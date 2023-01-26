package com.example.smev.service.Impl;

import com.example.smev.dao.FineRequest;
import com.example.smev.repo.FineRequestRepo;
import com.example.smev.service.RequestQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestQueueServiceImpl implements RequestQueueService {

    private final FineRequestRepo fineRequestRepo;

    @Override
    public ResponseEntity<FineRequest> saveFineRequestToQueue(FineRequest fineRequest) throws InterruptedException {
        log.info("Saving request to queue: {}",fineRequest);
        FineRequest request = fineRequestRepo.save(fineRequest);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Override
    public FineRequest getFineFromQueue(FineRequest fineRequest) {
        FineRequest request = new FineRequest();
        if (Objects.isNull(fineRequest.getTaxPayerID())){
            request = fineRequestRepo.findFirstByVehicleCertificate(fineRequest.getVehicleCertificate()).orElse(new FineRequest());
        }
        else if (Objects.isNull(fineRequest.getVehicleCertificate())){
            request = fineRequestRepo.findFirstByTaxPayerID(fineRequest.getTaxPayerID()).orElse(new FineRequest());
        }
        log.info("Got request from queue: {}",fineRequest);
        return request;
    }

    @Override
    public void deleteRequestFromQueue(FineRequest fineRequest) {
        log.info("Deleted request from queue: {}",fineRequest);
        fineRequestRepo.delete(fineRequest);
    }

}
