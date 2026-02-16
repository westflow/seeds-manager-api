package com.westflow.seeds_manager_api.application.port;

public interface EmailSender {

    void sendPasswordResetEmail(String to, String token);
}
