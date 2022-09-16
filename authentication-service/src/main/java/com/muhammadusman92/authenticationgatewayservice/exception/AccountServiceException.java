package com.muhammadusman92.authenticationgatewayservice.exception;

public class AccountServiceException extends RuntimeException{
    private String messsage;

    public AccountServiceException(String messsage) {
        super(messsage);
        this.messsage = messsage;
    }
}
