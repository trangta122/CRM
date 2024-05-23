package com.intern.crm.payload.response;

import org.springframework.http.HttpStatus;

public class ResponseError {
    private HttpStatus error;
    private String message;

    public ResponseError() {
    }

    public ResponseError(HttpStatus error, String message) {
        this.error = error;
        this.message = message;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
