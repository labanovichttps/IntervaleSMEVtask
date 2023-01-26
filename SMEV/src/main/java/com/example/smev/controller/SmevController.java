package com.example.smev.controller;

import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import com.example.smev.service.RequestQueueService;
import com.example.smev.service.ResponseQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fine/")
public class SmevController {

    private final RequestQueueService fineRequireService;
    private final ResponseQueueService fineResponseService;

    @PostMapping("/request")
    public ResponseEntity<FineRequest> requestFine(@RequestBody FineRequest fineRequest) throws InterruptedException {
        return fineRequireService.saveFineRequestToQueue(fineRequest);
    }
    @PostMapping("/result")
    public ResponseEntity<List<FineResponse>> getResult(@RequestBody FineRequest fineRequest){
        return fineResponseService.getFineResponse(fineRequest);
    }
    @PostMapping("/acknowledge")
    public ResponseEntity<HttpStatus> acknowledge(@RequestBody List<FineResponse> fineResponse){
        return fineResponseService.deleteResponseFromQueue(fineResponse);
    }
}
