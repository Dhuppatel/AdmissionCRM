package com.admissioncrm.applicationmgmtservice.Exception.Feign;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
