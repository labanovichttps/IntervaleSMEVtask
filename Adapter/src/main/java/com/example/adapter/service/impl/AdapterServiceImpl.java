package com.example.adapter.service.impl;

import com.example.adapter.dao.FineRequest;
import com.example.adapter.dao.FineResponse;
import com.example.adapter.service.AdapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdapterServiceImpl implements AdapterService {

    private final WebClient webClient;

    private final String FINE_REQUEST = "/request";
    private final String FINE_GET_RESULT = "/result";
    private final String FINE_SEND_ACKNOWLEDGE = "/acknowledge";


    public ResponseEntity<List<FineResponse>> requestFineFromSMEV(FineRequest fineRequest) {
        try {
            FineRequest request = requestFine(fineRequest).block();
            List<FineResponse> fineResponse = getResult(request).block();
            sendAcknowledge(fineResponse).block();
            return new ResponseEntity<>(fineResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    public Mono<FineRequest> requestFine(FineRequest fineRequest) {
        return webClient.post()
                .uri(FINE_REQUEST)
                .body(BodyInserters.fromValue(fineRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(FineRequest.class);
    }

    public Mono<List<FineResponse>> getResult(FineRequest fineRequest) {
        return webClient.post()
                .uri(FINE_GET_RESULT)
                .body(BodyInserters.fromValue(fineRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(new ParameterizedTypeReference<List<FineResponse>>() {})
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(3)));
    }

    public Mono<ResponseEntity> sendAcknowledge(List<FineResponse> fineResponse) {
        return webClient.post()
                .uri(FINE_SEND_ACKNOWLEDGE)
                .body(BodyInserters.fromValue(fineResponse))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(ResponseEntity.class);
    }
}
