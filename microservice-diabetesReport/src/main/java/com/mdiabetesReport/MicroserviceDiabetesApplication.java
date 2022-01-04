package com.mdiabetesReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mdiabetesReport")
public class MicroserviceDiabetesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceDiabetesApplication.class, args);
    }
}
