package com.admissioncrm.applicationmgmtservice.Exception.Feign;

class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
