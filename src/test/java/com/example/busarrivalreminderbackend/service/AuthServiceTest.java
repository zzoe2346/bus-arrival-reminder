package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.entity.Member;
import com.example.busarrivalreminderbackend.exception.AuthenticationFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings
class AuthServiceTest {

    @Mock
    JwtService jwtService;
    @Mock
    MemberService memberService;

    @InjectMocks
    AuthService authService;

    @Test
    @DisplayName("멤버 인증 성공")
    void authenticate_success() {
        //Given
        String email = "test@example.com";
        String password = "password";
        Member member = mock(Member.class);

        when(member.getId()).thenReturn(1L);
        when(member.getPassword()).thenReturn(password);
        when(memberService.findByEmail(email)).thenReturn(member);
        when(jwtService.createJwt(member.getId())).thenReturn("mockToken");

        //When
        String token = authService.authenticate(email, password);

        //Then
        assertNotNull(token);

    }

    @Test
    @DisplayName("멤버 인증 실패-비밀번호가 틀릴 경우")
    void authenticate_fail1() {
        //Given
        String email = "test@example.com";
        String password = "password";
        Member member = mock(Member.class);

        when(member.getPassword()).thenReturn("wrongPassword");
        when(memberService.findByEmail(email)).thenReturn(member);

        //When Then
        assertThrows(AuthenticationFailException.class, () -> {
            authService.authenticate(email, password);
        });

    }

    @Test
    @DisplayName("멤버 인증 실패-멤버가 null인 경우")
    void authenticate_fail2() {
        //Given
        String email = "test@example.com";
        String password = "password";
        Member member = null;

        //When Then
        assertThrows(AuthenticationFailException.class, () -> {
            authService.authenticate(email, password);
        });

    }

}
