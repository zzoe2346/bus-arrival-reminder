package com.example.busarrivalreminderbackend.controller;

import com.example.busarrivalreminderbackend.dto.SignUpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MemberController {

    @PostMapping("/api/signup")
    public void signUp(@RequestBody SignUpRequest request) {

    }

}
