package com.edu.planner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo () {

    return new OpenAPI().info(new Info().title("My API")
            .description("This is a sample Spring Boot RESTful service Swagger 3")
            .version("v1.0.0"));
    }
}
