package com.muhammadusman92.authenticationgatewayservice.payloads;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
