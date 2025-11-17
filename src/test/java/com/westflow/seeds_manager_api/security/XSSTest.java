package com.westflow.seeds_manager_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.westflow.seeds_manager_api.infrastructure.web.RequestSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class XSSTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new RequestSanitizer());
        mapper.registerModule(module);
    }

    @Test
    void sanitize_common_xss_payloads() throws Exception {
        String[] payloads = {
                "<script>alert('XSS')</script>",
                "<img src=x onerror=alert(1)>",
                "<svg/onload=alert('XSS')>",
                "<a href=\"javascript:alert(1)\">x</a>",
                "<body onload=alert('XSS')>",
        };

        for (String p : payloads) {
            String result = mapper.readValue("\"" + p + "\"", String.class);

            assertThat(result, not(containsString("<")));
            assertThat(result, containsString("&lt;"));
            assertThat(result, not(containsString("javascript:")));
        }
    }

    @Test
    void sanitize_script_tag() throws Exception {
        String input = "<script>alert('XSS')</script>";

        String result = mapper.readValue("\"" + input + "\"", String.class);

        assertThat(result, containsString("&lt;script"));
        assertThat(result, containsString("&lt;/script"));
        assertThat(result, containsString("&#39;XSS&#39;"));
    }
}
