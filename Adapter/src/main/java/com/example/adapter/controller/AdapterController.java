package com.example.adapter.controller;

import com.example.adapter.dao.FineRequest;
import com.example.adapter.dao.FineResponse;
import com.example.adapter.service.AdapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class AdapterController {

    private final AdapterService adapterService;

    @GetMapping("/fine/request")
    public ResponseEntity<FineResponse> getFine(@RequestBody FineRequest fineRequest) {
        return adapterService.requestFineFromSMEV(fineRequest);
    }
}
