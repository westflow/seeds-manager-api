package com.westflow.seeds_manager_api.api.dto.response;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String position;
    private AccessLevel accessLevel;
}
