package com.example.busarrivalreminderbackend.dto;

public record LoginRequest(
        String email,
        String password
) {
}
