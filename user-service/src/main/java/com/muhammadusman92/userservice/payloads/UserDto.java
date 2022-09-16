package com.muhammadusman92.userservice.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotNull(message = "name cannot be empty")
    private String name;
    @NotNull(message = "email cannot be empty")
    private String email;
    @NotNull(message = "password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private AccountDto accountDto;
}
