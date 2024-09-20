package com.example.busarrivalreminderbackend.controller;

import com.example.busarrivalreminderbackend.dto.CheckIdResponse;
import com.example.busarrivalreminderbackend.dto.SignUpRequest;
import com.example.busarrivalreminderbackend.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/members/signup")
    public void signUp(@RequestBody SignUpRequest request) {

    }

    @GetMapping("/api/members/check-email/{email}")
    public ResponseEntity<Void> checkEmailDuplication(@PathVariable String email) {

        memberService.checkEmailDuplication(email);
        return ResponseEntity.ok().build();

    }

}
