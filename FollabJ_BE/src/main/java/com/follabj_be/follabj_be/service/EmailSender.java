package com.follabj_be.follabj_be.service;

public interface EmailSender {
    void sendEmail(String to, String email, String subject);
}
