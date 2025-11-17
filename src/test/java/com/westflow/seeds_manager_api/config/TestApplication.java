package com.westflow.seeds_manager_api.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class
        },
        scanBasePackages = "com.westflow.seeds_manager_api"
)
@ComponentScan(
        basePackages = "com.westflow.seeds_manager_api",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "com\\.westflow\\.seeds_manager_api\\.infrastructure\\.config\\.SecurityConfig"
                )
        }
)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
