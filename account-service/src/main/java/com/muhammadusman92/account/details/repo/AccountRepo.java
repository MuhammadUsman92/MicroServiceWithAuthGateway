package com.muhammadusman92.account.details.repo;

import com.muhammadusman92.account.details.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {

    Optional<Account> findByUserId(Long userId);
}
