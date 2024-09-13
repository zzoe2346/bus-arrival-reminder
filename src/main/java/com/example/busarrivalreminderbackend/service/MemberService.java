package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.CheckIdResponse;
import com.example.busarrivalreminderbackend.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void signUp(String id, String password) {

    }

    public CheckIdResponse checkId(String id) {
        if (memberRepository.existsById(id)) {
            return new CheckIdResponse(true, "This Id is available");
        }
        return new CheckIdResponse(false, "Id already exists");
    }
}
