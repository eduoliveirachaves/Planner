package com.edu.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.edu")
@EntityScan(basePackages = "com.edu.planner.entity")
@EnableJpaRepositories("com.edu.planner.repositories")
public class PlannerApplication {

    public static final Logger log = LoggerFactory.getLogger(PlannerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlannerApplication.class, args);
        log.info("Planner Application started");
    }

}
