package com.admissioncrm.applicationmgmtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.admissioncrm.applicationmgmtservice.Feign")
@SpringBootApplication
public class ApplicationMgmtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMgmtServiceApplication.class, args);
    }

}
