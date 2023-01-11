package com.example.adapter.service.impl;

import com.example.adapter.dao.FineRequest;
import com.example.adapter.dao.FineResponse;
import com.example.adapter.service.AdapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AdapterServiceImpl implements AdapterService {

    private final WebClient webClient;

    private final String FINE_REQUEST = "/request";
    private final String FINE_GET_RESULT = "/result";
    private final String FINE_SEND_ACKNOWLEDGE = "/acknowledge";


    public FineResponse requestFineFromSMEV(FineRequest fineRequest){
        try {
            FineRequest request = requestFine(fineRequest).block();
            FineResponse fineResponse = getResult(request).block();
            sendAcknowledge(fineResponse);
            return fineResponse;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
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

    public Mono<FineResponse> getResult(FineRequest fineRequest) {
        return  webClient.post()
                .uri(FINE_GET_RESULT)
                .body(BodyInserters.fromValue(fineRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(FineResponse.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(3)));
    }

    public boolean sendAcknowledge(FineResponse fineResponse) {
        return true;
    }
}
