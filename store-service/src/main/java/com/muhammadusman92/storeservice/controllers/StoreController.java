package com.muhammadusman92.storeservice.controllers;

import com.muhammadusman92.storeservice.payloads.Response;
import com.muhammadusman92.storeservice.payloads.StoreDto;
import com.muhammadusman92.storeservice.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @PostMapping("/")
    public ResponseEntity<Response> createStore(@RequestBody StoreDto storeDto){
        StoreDto store=storeService.createStore(storeDto);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Store is successfully created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .data(store)
                .build(), CREATED);
    }
    @PutMapping("/{storeId}")
    public ResponseEntity<Response> updateStore(@RequestBody StoreDto storeDto,@PathVariable Long storeId){
        StoreDto store = storeService.updateStore(storeDto,storeId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Store is successfully updated")
                .status(OK)
                .statusCode(OK.value())
                .data(store)
                .build(),OK);
    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Response> deleteStore(@PathVariable Long storeId){
        storeService.deleteStoreById(storeId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Store deleted successfully")
                .status(OK)
                .statusCode(OK.value())
                .build(),OK);
    }
    @GetMapping("/")
    public ResponseEntity<Response> getAllStore(){
        List<StoreDto> storeDtos=storeService.getAllStores();
        return new  ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("All Store are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(storeDtos)
                .build(),OK);
    }
    @GetMapping("/{storeId}")
    public ResponseEntity<Response> getStoreById(@PathVariable Long storeId){
        StoreDto store=storeService.getStoreById(storeId);
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .message("Store with id "+storeId+" are successfully get")
                .status(OK)
                .statusCode(OK.value())
                .data(store)
                .build(),OK);
    }


}
