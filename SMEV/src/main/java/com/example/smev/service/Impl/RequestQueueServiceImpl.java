package com.example.smev.service.Impl;

import com.example.smev.dao.FineRequest;
import com.example.smev.repo.FineRequestRepo;
import com.example.smev.service.RequestQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestQueueServiceImpl implements RequestQueueService {

    private final FineRequestRepo fineRequestRepo;

    @Override
    public ResponseEntity<FineRequest> saveFineRequestToQueue(FineRequest fineRequest) throws InterruptedException {
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
        return request;
    }

    @Override
    public void deleteRequestFromQueue(FineRequest fineRequest) {
        fineRequestRepo.delete(fineRequest);
    }

}
