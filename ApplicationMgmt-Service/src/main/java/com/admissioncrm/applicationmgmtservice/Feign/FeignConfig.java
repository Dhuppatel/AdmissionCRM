package com.admissioncrm.applicationmgmtservice.Feign;

import com.admissioncrm.applicationmgmtservice.Exception.Feign.CustomFeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Add JWT token from current context
            String token = SecurityContextHolder.getContext()
                    .getAuthentication().getCredentials().toString();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}