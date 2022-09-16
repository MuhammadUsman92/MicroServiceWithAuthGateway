package com.muhammadusman92.authenticationgatewayservice.exception;

public class BadCredentialsExceptionCustom extends RuntimeException{
    private String message;
    public BadCredentialsExceptionCustom(String message) {
        super(message);
    }
}
