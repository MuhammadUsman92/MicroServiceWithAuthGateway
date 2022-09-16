package com.muhammadusman92.account.details.controllers;

import com.muhammadusman92.account.details.payloads.AccountDto;
import com.muhammadusman92.account.details.payloads.Response;
import com.muhammadusman92.account.details.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/")
    public ResponseEntity<Response> createAccount(@RequestBody AccountDto accountDto){
        AccountDto savedAccount=accountService.createAccount(accountDto);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Account is successfully created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .data(savedAccount)
                .build(), CREATED);
    }
    @PutMapping("/{accountId}")
    public ResponseEntity<Response> updateAccount(@RequestBody AccountDto accountDto,@PathVariable Long accountId){
        AccountDto updatedAccount = accountService.updateAccount(accountDto,accountId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Account is successfully updated")
                .status(OK)
                .statusCode(OK.value())
                .data(updatedAccount)
                .build(),OK);
    }
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Response> deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccountById(accountId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Account deleted successfully")
                .status(OK)
                .statusCode(OK.value())
                .build(),OK);
    }
    @GetMapping("/")
    public ResponseEntity<Response> getAllAccount(){
        List<AccountDto> accountDtoList=accountService.getAllAccount();
        return new  ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("All Account are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(accountDtoList)
                .build(),OK);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<Response> getAccountById(@PathVariable Long accountId){
        AccountDto account=accountService.getAccountById(accountId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Account with id "+accountId+" are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(account)
                .build(),OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response> getAccountByUserId(@PathVariable Long userId){
        AccountDto account=accountService.getAccountByUserId(userId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Account with user id "+userId+" are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(account)
                .build(),OK);
    }

}
