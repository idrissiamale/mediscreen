package com.clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.clientui")
public class ClientUIApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientUIApplication.class, args);
    }
}
