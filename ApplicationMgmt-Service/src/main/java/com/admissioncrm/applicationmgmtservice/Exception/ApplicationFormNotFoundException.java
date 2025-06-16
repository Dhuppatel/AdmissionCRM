package com.admissioncrm.applicationmgmtservice.Exception;




public class ApplicationFormNotFoundException extends RuntimeException {
    public ApplicationFormNotFoundException(String message) {
        super(message);
    }

    public ApplicationFormNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}




