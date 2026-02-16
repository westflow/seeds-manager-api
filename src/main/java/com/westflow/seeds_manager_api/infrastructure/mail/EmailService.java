package com.westflow.seeds_manager_api.infrastructure.mail;

import com.westflow.seeds_manager_api.application.port.EmailSender;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        String baseUrl = emailProperties.getFrontendUrl();
        String link = (baseUrl != null ? baseUrl : "http://localhost:3000") + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);

        if (StringUtils.isNotBlank(emailProperties.getFrom())) {
            message.setFrom(emailProperties.getFrom());
        }

        message.setSubject("Reset de senha");
        message.setText("Clique no link para resetar sua senha:\n" + link);

        mailSender.send(message);
    }
}
