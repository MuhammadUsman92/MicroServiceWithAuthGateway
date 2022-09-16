package com.muhammadusman92.storeservice.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDto {
    private Long id;
    private String name;
    private String contact;
    private String address;
    private String dimensions;
}
