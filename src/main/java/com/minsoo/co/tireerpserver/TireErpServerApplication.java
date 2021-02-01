package com.minsoo.co.tireerpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TireErpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TireErpServerApplication.class, args);
    }

}
