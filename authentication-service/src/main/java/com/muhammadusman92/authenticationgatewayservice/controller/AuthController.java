package com.muhammadusman92.authenticationgatewayservice.controller;

import com.muhammadusman92.authenticationgatewayservice.exception.BadCredentialsExceptionCustom;
import com.muhammadusman92.authenticationgatewayservice.payloads.RegisterDto;
import com.muhammadusman92.authenticationgatewayservice.payloads.Response;
import com.muhammadusman92.authenticationgatewayservice.payloads.UserResponse;
import com.muhammadusman92.authenticationgatewayservice.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/")
    public ResponseEntity<String> authentication(){
        return new ResponseEntity<>("OK",OK);
    }


    @PostMapping("/login")
    public ResponseEntity<Response> createToken(
            @RequestParam(name = "email",required = true) String email,
            @RequestParam(name = "password",required = true) String password
    ) {
        this.authenticate(email,password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String generateToken = jwtTokenHelper.generateToken(userDetails);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(OK)
                .statusCode(OK.value())
                .token(generateToken)
                .build(), OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            throw new BadCredentialsExceptionCustom("BadCredentialsException");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterDto registerDto) {
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        UserResponse userResponse = restTemplate.postForObject(
                "http://localhost:8080/api/users/",
                registerDto,
                UserResponse.class);
        return new ResponseEntity(userResponse,userResponse.getStatus());
    }
}
