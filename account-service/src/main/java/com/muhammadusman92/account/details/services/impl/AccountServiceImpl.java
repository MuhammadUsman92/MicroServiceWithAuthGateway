package com.muhammadusman92.account.details.services.impl;

import com.muhammadusman92.account.details.entity.Account;
import com.muhammadusman92.account.details.exception.ResourceNotFoundException;
import com.muhammadusman92.account.details.payloads.AccountDto;
import com.muhammadusman92.account.details.repo.AccountRepo;
import com.muhammadusman92.account.details.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto,Account.class);
        Account saveAccount = accountRepo.save(account);
        return modelMapper.map(saveAccount,AccountDto.class);
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto, Long accountId) {
        Account account = modelMapper.map(accountDto,Account.class);
        Account updateAccount = accountRepo.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException("Account","accountId",accountId));
        account.setId(updateAccount.getId());
        Account saveAccount = accountRepo.save(account);
        return modelMapper.map(saveAccount,AccountDto.class);
    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException("Account","accountId",accountId));
        return modelMapper.map(account,AccountDto.class);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> accountList = accountRepo.findAll();
        return accountList.stream().map(account -> modelMapper.map(account,AccountDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccountById(Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException("Account","accountId",accountId));
        accountRepo.delete(account);
    }

    @Override
    public AccountDto getAccountByUserId(Long userId) {
        Account account = accountRepo.findByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("Account","User Id",userId));
        return modelMapper.map(account,AccountDto.class);
    }
}
