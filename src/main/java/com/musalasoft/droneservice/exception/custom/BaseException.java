package com.musalasoft.droneservice.exception.custom;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus statusCode;
    private String error;

    public BaseException(HttpStatus statusCode, String error) {
        this.statusCode = statusCode;
        this.error = error;
    }

    public BaseException(String error) {
        this.error = error;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}