package org.ensa.serviceanalyses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableFeignClients
public class ServiceAnalysesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAnalysesApplication.class, args);
    }

}
