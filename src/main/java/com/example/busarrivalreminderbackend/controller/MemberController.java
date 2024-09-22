package com.example.busarrivalreminderbackend.controller;

import com.example.busarrivalreminderbackend.dto.LoginRequest;
import com.example.busarrivalreminderbackend.dto.SignUpRequest;
import com.example.busarrivalreminderbackend.service.AuthService;
import com.example.busarrivalreminderbackend.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping("/api/members/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {

        memberService.signUp(request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/api/members/check-email/{email}")
    public ResponseEntity<Void> checkEmailDuplication(@PathVariable String email) {

        memberService.checkEmailDuplication(email);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/api/members/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        String token = authService.authenticate(request.email(), request.password());
        return ResponseEntity.ok(token);

    }

}
