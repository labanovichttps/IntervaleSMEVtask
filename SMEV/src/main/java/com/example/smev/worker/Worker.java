package com.example.smev.worker;

import com.example.smev.dao.Fine;
import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import com.example.smev.repo.FineRepo;
import com.example.smev.repo.FineRequestRepo;
import com.example.smev.repo.FineResponseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class Worker extends Thread{

    private final FineRequestRepo fineRequestRepo;
    private final FineRepo fineRepo;
    private final FineResponseRepo fineResponseRepo;

    @Override
    public void run() {
        log.info("Worker started");
        while (true) {
            List<FineRequest> requests = fineRequestRepo.findAll();
            for (FineRequest fineRequest : requests) {
                Fine fineFromGismp = new Fine();
                FineRequest request;
                if (Objects.isNull(fineRequest.getTaxPayerID())) {
                    request = fineRequestRepo
                            .findFirstByVehicleCertificate(fineRequest.getVehicleCertificate())
                            .orElse(new FineRequest());
                    fineFromGismp = fineRepo.findFineByVehicleCertificate(request.getVehicleCertificate()).orElse(new Fine());
                } else if (Objects.isNull(fineRequest.getVehicleCertificate())) {
                    request = fineRequestRepo
                            .findFirstByTaxPayerID(fineRequest.getTaxPayerID())
                            .orElse(new FineRequest());
                    fineFromGismp = fineRepo.findFineByTaxPayerID(request.getTaxPayerID()).orElse(new Fine());
                }
                FineResponse fineResponse = FineResponse.builder()
                        .taxPayerID(fineFromGismp.getTaxPayerID())
                        .vehicleCertificate(fineFromGismp.getVehicleCertificate())
                        .article(fineFromGismp.getArticle())
                        .resolution(fineFromGismp.getResolution())
                        .fineAmount(fineFromGismp.getFineAmount())
                        .accruedAmount(fineFromGismp.getAccruedAmount())
                        .resolutionDate(fineFromGismp.getResolutionDate())
                        .build();
                fineResponseRepo.save(fineResponse);
                fineRequestRepo.delete(fineRequest);
            }
        }
    }
}
