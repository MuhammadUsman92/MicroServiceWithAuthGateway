package com.muhammadusman92.account.details.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    private Long userId;
    private String name;
    private String type;
    private Long balance;
    private Long withDrawLimit;
}
