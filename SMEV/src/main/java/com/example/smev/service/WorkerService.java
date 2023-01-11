package com.example.smev.service;

import com.example.smev.dao.FineRequest;

public interface WorkerService {

    public void emulateGISMP(FineRequest fineRequest) throws InterruptedException;
}
