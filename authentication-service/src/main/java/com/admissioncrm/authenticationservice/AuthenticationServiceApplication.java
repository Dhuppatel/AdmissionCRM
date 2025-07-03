package com.admissioncrm.authenticationservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("D:\\VK18\\My Projects\\Admission CRM\\Admission_CRM_Backend_Connect\\authentication-service\\.env").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}
