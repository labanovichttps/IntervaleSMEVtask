package com.example.smev.worker;

import com.example.smev.dao.Fine;
import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import com.example.smev.exception.FineNotFoundException;
import com.example.smev.repo.FineRepo;
import com.example.smev.repo.FineRequestRepo;
import com.example.smev.repo.FineResponseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                log.info("Processing request {}",fineRequest);
                List<Fine> fineFromGismp = new ArrayList<>();
                try {
                    if (Objects.isNull(fineRequest.getTaxPayerID())) {
                        fineFromGismp = fineRepo.findAllByVehicleCertificate(fineRequest.getVehicleCertificate())
                                .orElseThrow(FineNotFoundException::new);
                    } else if (Objects.isNull(fineRequest.getVehicleCertificate())) {
                        fineFromGismp = fineRepo.findAllByTaxPayerID(fineRequest.getTaxPayerID())
                                .orElseThrow(FineNotFoundException::new);
                    }
                }catch (Exception e){
                    log.error("Error with processing {}", fineRequest);
                    e.printStackTrace();
                }
                List<FineResponse> fineResponse = new ArrayList<>();
                for (Fine fine : fineFromGismp) {
                    fineResponse.add(new FineResponse(fine));
                }
                fineResponseRepo.saveAll(fineResponse);
                fineRequestRepo.delete(fineRequest);
            }
        }
    }
    public void initQueue(){

    }
}
