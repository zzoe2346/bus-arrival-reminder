package com.example.busarrivalreminderbackend.controller.advice;

import com.example.busarrivalreminderbackend.exception.AuthenticationFailException;
import com.example.busarrivalreminderbackend.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "busarrivalreminderbackend.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {

        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

    }

    @ExceptionHandler(AuthenticationFailException.class)
    public ProblemDetail handleAuthenticationFailException(AuthenticationFailException e) {

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());

    }

}
