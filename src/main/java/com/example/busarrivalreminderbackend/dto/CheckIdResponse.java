package com.example.busarrivalreminderbackend.dto;

public class CheckIdResponse {

    private boolean success;
    private String message;

    public CheckIdResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

