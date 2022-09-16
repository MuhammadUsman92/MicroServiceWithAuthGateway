package com.muhammadusman92.storeservice.repo;

import com.muhammadusman92.storeservice.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store,Long> {
}
