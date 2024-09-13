package com.example.busarrivalreminderbackend.controller;

import com.example.busarrivalreminderbackend.dto.SignUpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class MemberController {

    @PostMapping("/api/members/signup")
    public void signUp(@RequestBody SignUpRequest request) {

    }

    @GetMapping("/api/members/check-id")
    public void checkId(@RequestParam String id) {

    }

}
