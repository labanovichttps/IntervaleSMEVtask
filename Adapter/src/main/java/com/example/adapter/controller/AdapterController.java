package com.example.adapter.controller;

import com.example.adapter.dao.FineRequest;
import com.example.adapter.dao.FineResponse;
import com.example.adapter.service.impl.AdapterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class AdapterController {

    private final AdapterServiceImpl adapterService;

    @GetMapping("/fine/request")
    public ResponseEntity<FineResponse> getFine(@RequestBody FineRequest fineRequest){
        return adapterService.requestFineFromSMEV(fineRequest);
    }
}
