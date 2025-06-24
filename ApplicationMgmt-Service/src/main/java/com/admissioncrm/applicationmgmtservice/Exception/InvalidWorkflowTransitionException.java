package com.admissioncrm.applicationmgmtservice.Exception;

public class InvalidWorkflowTransitionException extends RuntimeException {
    public InvalidWorkflowTransitionException(String message) {
        super(message);
    }
}
