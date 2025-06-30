package com.admissioncrm.applicationmgmtservice.Exception.Feign;

// Custom Exception Classes
 public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

