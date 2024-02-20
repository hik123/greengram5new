package com.green.greengram4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@EnableJpaAuditing  // Auditing 사용하기위해서
@ConfigurationPropertiesScan
@SpringBootApplication
public class Greengram5Application {

    public static void main(String[] args) {
        SpringApplication.run(Greengram5Application.class, args);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer() {
        return p -> p.setOneIndexedParameters(true); //
    }

    //@FunctionalInterface  // 함수형인터페이스
    //public interface PageableHandlerMethodArgumentResolverCustomizer 추상메소드가 하나
}
