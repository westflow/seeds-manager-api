package com.westflow.seeds_manager_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.westflow.seeds_manager_api.api.controller.LotReservationController;
import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.application.service.LotReservationService;
import com.westflow.seeds_manager_api.config.TestConfig;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import com.westflow.seeds_manager_api.infrastructure.config.WebMvcConfig;
import com.westflow.seeds_manager_api.infrastructure.security.CurrentUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LotReservationController.class)
@Import({TestConfig.class, WebMvcConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class XssWebMvcTest {

    private static final String XSS_PAYLOAD = "<script>";
    private static final String ENCODED_XSS_PAYLOAD = "&lt;script&gt;";

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LotReservationService lotReservationService;

    @MockBean
    private CurrentUserArgumentResolver currentUserArgumentResolver;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("hashedpassword")
                .name("Test User")
                .position("Developer")
                .accessLevel(AccessLevel.ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .active(true)
                .build();

        when(currentUserArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(currentUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(testUser);

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        LotReservationResponse mockResponse = new LotReservationResponse();
        mockResponse.setId(1L);
        mockResponse.setLotId(1L);
        mockResponse.setIdentification(XSS_PAYLOAD);
        mockResponse.setQuantity(BigDecimal.TEN);
        mockResponse.setReservationDate(LocalDate.now());
        mockResponse.setStatus(LotStatus.RESERVED);
        mockResponse.setClientId(1L);
        mockResponse.setUserId(1L);
        mockResponse.setCreatedAt(LocalDateTime.now());
        mockResponse.setUpdatedAt(LocalDateTime.now());

        when(lotReservationService.reserve(any(), any())).thenReturn(mockResponse);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenCreateWithXss_thenSanitizeInput() throws Exception {

        LotReservationRequest request = new LotReservationRequest();
        request.setLotId(1L);
        request.setIdentification(XSS_PAYLOAD);
        request.setQuantity(BigDecimal.TEN);
        request.setClientId(1L);

        MvcResult result = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(ENCODED_XSS_PAYLOAD),
                "Response should contain sanitized XSS payload. Expected: " + ENCODED_XSS_PAYLOAD +
                        ", Actual: " + responseBody);

        assertFalse(responseBody.contains(XSS_PAYLOAD),
                "Response should not contain raw XSS payload. Actual: " + responseBody);
    }
}