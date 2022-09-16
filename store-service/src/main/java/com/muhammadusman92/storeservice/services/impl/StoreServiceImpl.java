package com.muhammadusman92.storeservice.services.impl;

import com.muhammadusman92.storeservice.entity.Store;
import com.muhammadusman92.storeservice.exception.ResourceNotFoundException;
import com.muhammadusman92.storeservice.payloads.StoreDto;
import com.muhammadusman92.storeservice.repo.StoreRepo;
import com.muhammadusman92.storeservice.services.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public StoreDto createStore(StoreDto storeDto) {
        Store store = modelMapper.map(storeDto, Store.class);
        Store saveStore = storeRepo.save(store);
        return modelMapper.map(saveStore,StoreDto.class);
    }

    @Override
    public StoreDto updateStore(StoreDto storeDto, Long storeId) {
        Store store = modelMapper.map(storeDto,Store.class);
        Store updateStore = storeRepo.findById(storeId)
                .orElseThrow(()->new ResourceNotFoundException("Store","store Id",storeId));
        store.setId(updateStore.getId());
        Store saveStore = storeRepo.save(store);
        return modelMapper.map(saveStore,StoreDto.class);
    }

    @Override
    public StoreDto getStoreById(Long storeId) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(()->new ResourceNotFoundException("Store","store Id",storeId));
        return modelMapper.map(store,StoreDto.class);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> storeList = storeRepo.findAll();
        return storeList.stream().map(store -> modelMapper.map(store,StoreDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteStoreById(Long storeId) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",storeId));
        storeRepo.delete(store);
    }

}
