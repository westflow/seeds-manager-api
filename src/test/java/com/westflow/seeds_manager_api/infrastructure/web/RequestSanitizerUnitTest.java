package com.westflow.seeds_manager_api.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class RequestSanitizerUnitTest {

    @Test
    void requestSanitizer_escapesScriptTag() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new RequestSanitizer());
        mapper.registerModule(module);

        String raw = "<script>alert('XSS')</script>";
        String json = "\"" + raw + "\"";
        String result = mapper.readValue(json, String.class);
        assertThat(result, containsString("&lt;script"));
    }
}
