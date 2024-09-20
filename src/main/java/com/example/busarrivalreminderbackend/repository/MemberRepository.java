package com.example.busarrivalreminderbackend.repository;

import com.example.busarrivalreminderbackend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsByEmail(String email);

}
