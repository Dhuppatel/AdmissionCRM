package com.admissioncrm.authenticationservice;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.ws.rs.core.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;

@SpringBootApplication
public class AuthenticationServiceApplication {

    @Value("${env.path")
    private static String envPath;

    public static void main(String[] args) {
        String currentDir = "E:\\AdmissionCRM\\authentication-service\\.env";
        Dotenv dotenv = Dotenv.configure().directory(currentDir).load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}
