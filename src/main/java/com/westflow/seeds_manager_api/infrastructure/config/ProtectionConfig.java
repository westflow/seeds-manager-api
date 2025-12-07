package com.westflow.seeds_manager_api.infrastructure.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.westflow.seeds_manager_api.infrastructure.web.ProtectionSerializer;
import com.westflow.seeds_manager_api.infrastructure.web.RequestSanitizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ProtectionConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .modules(protectionModule(), new JavaTimeModule())
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();
    }

    @Bean
    public SimpleModule protectionModule() {
        SimpleModule module = new SimpleModule("ProtectionModule");
        module.addDeserializer(String.class, new RequestSanitizer());
        module.addSerializer(String.class, new ProtectionSerializer());
        return module;
    }
}
