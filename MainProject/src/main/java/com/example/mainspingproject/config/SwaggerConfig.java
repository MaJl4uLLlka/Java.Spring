package com.example.mainspingproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Open-API documentation for Meetup Service")
                                .version("1.0.0.1")
                                .contact(
                                        new Contact()
                                                .email("kantorkazimir@gmail.com")
                                                .url("https://github.com/MaJl4uLLlka")
                                                .name("Kazimir  Kantor")
                                )
                );
    }
}
