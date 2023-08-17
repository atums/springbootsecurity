package com.apys.learning.springbootsecurity.rest;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}
