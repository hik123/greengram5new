package com.green.greengram4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // Auditing 사용하기위해서
@ConfigurationPropertiesScan
@SpringBootApplication
public class Greengram5Application {

    public static void main(String[] args) {
        SpringApplication.run(Greengram5Application.class, args);
    }

}
