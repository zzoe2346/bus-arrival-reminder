package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.entity.Member;
import com.example.busarrivalreminderbackend.exception.AuthenticationFailException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberService memberService;
    private final JwtService jwtService;


    public AuthService(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    public String authenticate(String email, String password) {

        Member member = memberService.findByEmail(email);

        if (member == null || !member.getPassword().equals(password)) {
            throw new AuthenticationFailException("Invalid credentials");
        }

        return jwtService.createJwt(member.getId());

    }

}
