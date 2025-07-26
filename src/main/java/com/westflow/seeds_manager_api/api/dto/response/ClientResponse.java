package com.westflow.seeds_manager_api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {

    private Long id;
    private String number;
    private String name;
    private String email;
    private String phone;
}
