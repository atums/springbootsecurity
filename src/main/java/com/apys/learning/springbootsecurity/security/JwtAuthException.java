package com.apys.learning.springbootsecurity.security;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthException extends AuthenticationException {

    private HttpStatus status;

    public JwtAuthException(String msg) {
        super(msg);
    }

    public JwtAuthException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

}
