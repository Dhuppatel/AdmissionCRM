package com.admissioncrm.authenticationservice;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.ws.rs.core.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.net.URL;

@SpringBootApplication
@EnableFeignClients
public class AuthenticationServiceApplication {

    @Value("${env.path")
    private static String envPath;

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir") + "/authentication-service";
        Dotenv dotenv = Dotenv.configure().directory(currentDir).load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}
