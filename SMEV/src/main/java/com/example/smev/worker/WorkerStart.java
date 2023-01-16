package com.example.smev.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkerStart {
    private final Worker worker;

    @PostConstruct
    public void start(){
        worker.start();
    }
}
