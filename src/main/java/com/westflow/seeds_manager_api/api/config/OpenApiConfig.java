package com.westflow.seeds_manager_api.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI seedsManagerOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Seeds Manager API")
                .version("v1.0")
                .description("Gerenciamento de sementes")
                .contact(new Contact()
                    .name("WestFlow Team")
                    .email("admin@westflow.com")))
            .components(new Components()
                    .addSecuritySchemes("bearer-jwt",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
    }
}
