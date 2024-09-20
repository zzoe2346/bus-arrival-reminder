package com.example.busarrivalreminderbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.DuplicateMappingException;
import org.springframework.dao.DataIntegrityViolationException;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String email;
    String password;

    public Member() {
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
