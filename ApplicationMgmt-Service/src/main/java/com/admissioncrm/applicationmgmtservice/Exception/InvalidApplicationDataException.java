package com.admissioncrm.applicationmgmtservice.Exception;

public class InvalidApplicationDataException extends RuntimeException {
    public InvalidApplicationDataException(String message) {
        super(message);
    }
}