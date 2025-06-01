package com.backend.assetmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AssetmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssetmanagementApplication.class, args);
	}
}