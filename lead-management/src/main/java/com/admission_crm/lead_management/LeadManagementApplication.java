package com.admission_crm.lead_management;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class LeadManagementApplication {
	public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir") + "/lead-management";
		Dotenv dotenv = Dotenv.configure().directory(currentDir).load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(LeadManagementApplication.class, args);
	}

}
