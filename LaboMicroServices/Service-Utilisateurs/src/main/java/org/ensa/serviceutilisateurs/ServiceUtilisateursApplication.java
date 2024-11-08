package org.ensa.serviceutilisateurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceUtilisateursApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUtilisateursApplication.class, args);
    }

}
