package com.admissioncrm.applicationmgmtservice.Exception.Feign;

class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
