package com.BloggingApp.utils;

import java.util.Date;

public class ErrorDetails {

    private Date httpStatus;
    private String message;
    private String details;

    public ErrorDetails(Date httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public ErrorDetails(Date httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
    }

    public Date getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
