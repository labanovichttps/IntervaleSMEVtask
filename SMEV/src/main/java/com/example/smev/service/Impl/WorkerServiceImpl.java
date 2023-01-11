package com.example.smev.service.Impl;

import com.example.smev.dao.Fine;
import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import com.example.smev.repo.FineRepo;
import com.example.smev.repo.FineRequestRepo;
import com.example.smev.repo.FineResponseRepo;
import com.example.smev.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final FineRequestRepo fineRequestRepo;
    private final FineRepo fineRepo;
    private final FineResponseRepo fineResponseRepo;

    @Override
    @Async
    public void emulateGISMP(FineRequest fineRequest) throws InterruptedException {

        Thread.sleep(1000); //Эмуляция задержки работы СМЭВ
        FineRequest request;
        Fine fineFromGismp = new Fine();
        if (fineRequest.getTaxPayerID().isEmpty()) {
            request = fineRequestRepo
                    .findFineRequestByVehicleCertificate(fineRequest.getVehicleCertificate())
                    .orElse(new FineRequest());
            fineFromGismp = fineRepo.findFineByVehicleCertificate(request.getVehicleCertificate()).orElse(new Fine());
        } else if (fineRequest.getVehicleCertificate().isEmpty()) {
            request = fineRequestRepo
                    .findFineRequestByTaxPayerID(fineRequest.getTaxPayerID())
                    .orElse(new FineRequest());
            fineFromGismp = fineRepo.findFineByTaxPayerID(request.getTaxPayerID()).orElse(new Fine());
        }
        FineResponse fineResponse = new FineResponse();
        fineResponse.setTaxPayerID(fineFromGismp.getTaxPayerID());
        fineResponse.setVehicleCertificate(fineFromGismp.getVehicleCertificate());
        fineResponse.setArticle(fineFromGismp.getArticle());
        fineResponse.setResolution(fineFromGismp.getResolution());
        fineResponse.setFineAmount(fineFromGismp.getFineAmount());
        fineResponse.setAccruedAmount(fineFromGismp.getAccruedAmount());
        fineResponse.setResolutionDate(fineFromGismp.getResolutionDate());
        fineResponseRepo.save(fineResponse);
        fineRequestRepo.delete(fineRequest);
    }
}
