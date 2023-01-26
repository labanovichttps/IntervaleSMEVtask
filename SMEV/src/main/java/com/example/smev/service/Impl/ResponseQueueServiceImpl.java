package com.example.smev.service.Impl;

import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import com.example.smev.repo.FineResponseRepo;
import com.example.smev.service.ResponseQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResponseQueueServiceImpl implements ResponseQueueService {

    private final FineResponseRepo fineResponseRepo;

    public ResponseEntity<List<FineResponse>> getFineResponse(FineRequest fineRequest) {
        List<FineResponse> fineResponse;
        if (Objects.isNull(fineRequest.getTaxPayerID())) {
            fineResponse = fineResponseRepo.findAllByVehicleCertificate(fineRequest.getVehicleCertificate()).orElseThrow();
        } else if (Objects.isNull(fineRequest.getVehicleCertificate())) {
            fineResponse = fineResponseRepo.findAllByTaxPayerID(fineRequest.getTaxPayerID()).orElseThrow();
        }
        else {
            return new ResponseEntity<>(HttpStatus.PROCESSING);
        }
        return new ResponseEntity<>(fineResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteResponseFromQueue(List<FineResponse> fineResponse) {
        fineResponseRepo.deleteAll(fineResponse);
        log.info("{} Deleted from ResponseQueue", fineResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
