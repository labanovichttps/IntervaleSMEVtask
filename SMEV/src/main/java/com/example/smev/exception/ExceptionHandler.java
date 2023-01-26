package com.example.smev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(FineNotFoundException.class)
    protected ResponseEntity<Object> fineNotFoundException (){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
