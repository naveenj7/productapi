package com.example.productapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI productOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product API")
                        .description("Spring Boot REST API for Product Management")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Product API Docs")
                        .url("http://localhost:8080/swagger-ui.html"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("product-api")
                .pathsToMatch("/api/**")
                .build();
    }
}

