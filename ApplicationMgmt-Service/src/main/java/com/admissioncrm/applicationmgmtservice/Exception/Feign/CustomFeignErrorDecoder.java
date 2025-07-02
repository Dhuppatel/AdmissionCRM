package com.admissioncrm.applicationmgmtservice.Exception.Feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 401:
                return new UnauthorizedException("Authentication token is invalid or expired");
            case 403:
                return new ForbiddenException("Access denied - insufficient permissions");
            case 404:
                return new NotFoundException("Resource not found: " + methodKey);
            case 500:
                return new InternalServerErrorException("Internal server error occurred");
            case 503:
                return new ServiceUnavailableException("Service is temporarily unavailable");
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }
}
