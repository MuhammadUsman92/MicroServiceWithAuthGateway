package com.muhammadusman92.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Role {
    @Id
    private Long id;
    private String name;
}
