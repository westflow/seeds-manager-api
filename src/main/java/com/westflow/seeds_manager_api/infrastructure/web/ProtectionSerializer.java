package com.westflow.seeds_manager_api.infrastructure.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.owasp.encoder.Encode;

import java.io.IOException;

public class ProtectionSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value == null ? null : Encode.forHtml(value));
    }
}
