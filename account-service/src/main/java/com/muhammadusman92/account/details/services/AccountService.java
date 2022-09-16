package com.muhammadusman92.account.details.services;


import com.muhammadusman92.account.details.payloads.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto updateAccount(AccountDto accountDto,Long accountId);
    AccountDto getAccountById(Long accountId);
    List<AccountDto> getAllAccount();
    void deleteAccountById(Long accountId);

    AccountDto getAccountByUserId(Long userId);
}
