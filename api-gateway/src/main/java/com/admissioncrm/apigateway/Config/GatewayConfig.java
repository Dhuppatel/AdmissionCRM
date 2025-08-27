package com.admissioncrm.apigateway.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class GatewayConfig {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    @Bean
    public RouterFunction<ServerResponse> gatewayRouterFunction() {
        return route()
                .GET("/gateway/info", request -> {
                    logger.info("Gateway info endpoint accessed");
                    return ServerResponse.ok().body("AdmissionCRM API Gateway is running");
                })
                .GET("/gateway/health", request -> {
                    return ServerResponse.ok().body("{\"status\": \"UP\", \"service\": \"api-gateway\"}");
                })
                .build();
    }
}