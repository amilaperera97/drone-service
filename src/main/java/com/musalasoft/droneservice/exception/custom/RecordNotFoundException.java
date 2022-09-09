package com.musalasoft.droneservice.exception.custom;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(HttpStatus statusCode, String error) {
        super(statusCode, error);
    }
}