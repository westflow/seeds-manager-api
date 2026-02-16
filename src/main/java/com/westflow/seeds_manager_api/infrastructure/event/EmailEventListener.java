package com.westflow.seeds_manager_api.infrastructure.event;

import com.westflow.seeds_manager_api.application.port.EmailSender;
import com.westflow.seeds_manager_api.domain.event.PasswordResetRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailSender emailSender;

    @Async
    @EventListener
    public void handlePasswordReset(PasswordResetRequestedEvent event) {
        emailSender.sendPasswordResetEmail(event.email(), event.token());
    }
}
