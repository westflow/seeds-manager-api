package com.westflow.seeds_manager_api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private String number;
    private String name;
    private String email;
    private String phone;
}
