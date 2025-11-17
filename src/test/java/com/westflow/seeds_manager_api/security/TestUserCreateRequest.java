package com.westflow.seeds_manager_api.security;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestUserCreateRequest {
    @NotBlank
    private String email;
    
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    
    @Size(max = 100)
    private String position;
    
    @NotNull
    private AccessLevel accessLevel;
}
