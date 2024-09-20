package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.entity.Member;
import com.example.busarrivalreminderbackend.exception.EmailAlreadyExistsException;
import com.example.busarrivalreminderbackend.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void signUp(String id, String password) {

    }

    }

    public void checkEmailDuplication(String email) {

        if (memberRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }

    }

}
