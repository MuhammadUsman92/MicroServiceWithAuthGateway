package com.muhammadusman92.storeservice.services;


import com.muhammadusman92.storeservice.payloads.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto);
    StoreDto updateStore(StoreDto storeDto,Long storeId);
    StoreDto getStoreById(Long storeId);
    List<StoreDto> getAllStores();
    void deleteStoreById(Long storeId);
}
