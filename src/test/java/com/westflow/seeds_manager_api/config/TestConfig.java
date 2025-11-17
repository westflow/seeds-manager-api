package com.westflow.seeds_manager_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.westflow.seeds_manager_api.infrastructure.config.ProtectionConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig extends ProtectionConfig {
    
    @Override
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = super.objectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        return objectMapper;
    }
}
