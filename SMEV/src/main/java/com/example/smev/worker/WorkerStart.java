package com.example.smev.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkerStart {
    private final Worker worker;

    @PostConstruct
    public void start(){
        worker.setName("Worker");
        log.info("Worker Started");
        worker.start();
    }
    @PreDestroy
    public void close(){
        worker.interrupt();
    }
}
