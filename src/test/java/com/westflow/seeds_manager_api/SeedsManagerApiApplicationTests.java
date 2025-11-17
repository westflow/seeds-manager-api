package com.westflow.seeds_manager_api;

import com.westflow.seeds_manager_api.config.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    classes = TestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class SeedsManagerApiApplicationTests {

    @Test
    void contextLoads() {}
}
