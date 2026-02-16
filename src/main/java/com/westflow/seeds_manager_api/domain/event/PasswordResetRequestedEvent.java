package com.westflow.seeds_manager_api.domain.event;

public record PasswordResetRequestedEvent(
        String email,
        String token
) {
}
