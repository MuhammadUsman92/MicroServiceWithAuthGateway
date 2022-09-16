package com.muhammadusman92.userservice.controller;


import com.muhammadusman92.userservice.model.User;
import com.muhammadusman92.userservice.payloads.AccountDto;
import com.muhammadusman92.userservice.payloads.AccountResponse;
import com.muhammadusman92.userservice.payloads.Response;
import com.muhammadusman92.userservice.payloads.UserDto;
import com.muhammadusman92.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.commons.logging.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpLogging;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final String ACCOUNT_SERVICE = "accountService";

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;
    protected final Log logger = HttpLogging.forLogName(this.getClass());

    @PostMapping("/")
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserDto userDto){
        UserDto savedUser=userService.createUser(userDto);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("User is successfully created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .data(savedUser)
                .build(), CREATED);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<Response> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Long userId){
        UserDto updatedUser = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("User is successfully updated")
                .status(OK)
                .statusCode(OK.value())
                .data(updatedUser)
                .build(),OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("User deleted successfully")
                .status(OK)
                .statusCode(OK.value())
                .build(),OK);
    }
    @GetMapping("/")
    public ResponseEntity<Response> getAllUser(){
        List<UserDto> users=userService.getAllUsers();
        return new  ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("All Users are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(users)
                .build(),OK);
    }

    @CircuitBreaker(name = ACCOUNT_SERVICE, fallbackMethod = "accountServiceFallback")
    @Retry(name = ACCOUNT_SERVICE)
    @RateLimiter(name = ACCOUNT_SERVICE)
    @GetMapping("/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable Long userId){
        UserDto userDto=userService.getUserById(userId);
        userDto.setAccountDto(getUserAccount(userDto.getId()));
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("User with id "+userId+" are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(userDto)
                .build(),OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Response> getUserByEmail(@PathVariable String email){
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(OK)
                .statusCode(OK.value())
                .data(user)
                .build(),OK);
    }


    public AccountDto getUserAccount(Long userId){
        AccountResponse<AccountDto> accountResponse=restTemplate.getForObject(
                "http://ACCOUNT-SERVICE/accounts/user/"+userId,AccountResponse.class);
        return modelMapper.map(accountResponse.getData(),AccountDto.class);
    }
    public ResponseEntity<Response> accountServiceFallback(Exception e) {
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(SERVICE_UNAVAILABLE)
                .statusCode(SERVICE_UNAVAILABLE.value())
                .message("Account Service is taking longer than Expected. Please try again later")
                .build()
                ,SERVICE_UNAVAILABLE);
    }
}
