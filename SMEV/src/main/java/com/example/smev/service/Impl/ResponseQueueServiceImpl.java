package com.example.smev.service.Impl;

import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import com.example.smev.repo.FineResponseRepo;
import com.example.smev.service.ResponseQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseQueueServiceImpl implements ResponseQueueService {

    private final FineResponseRepo fineResponseRepo;

    public ResponseEntity<FineResponse> getFineResponse(FineRequest fineRequest) {
        FineResponse fineResponse = new FineResponse();
        if (fineRequest.getTaxPayerID().isEmpty()) {
            fineResponse = fineResponseRepo.findFirstByVehicleCertificate(fineRequest.getVehicleCertificate()).orElse(new FineResponse());
        } else if (fineRequest.getVehicleCertificate().isEmpty()) {
            fineResponse = fineResponseRepo.findFirstByTaxPayerID(fineRequest.getTaxPayerID()).orElse(new FineResponse());
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fineResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteResponseFromQueue(FineResponse fineResponse) {
        fineResponseRepo.delete(fineResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
