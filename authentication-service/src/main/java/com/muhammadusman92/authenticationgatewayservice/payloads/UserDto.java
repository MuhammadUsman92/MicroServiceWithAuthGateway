package com.muhammadusman92.authenticationgatewayservice.payloads;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDto implements UserDetails {

    private String name;
    private String email;
    private String password;
    private boolean deleted;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<RoleDto> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(roleDto -> new SimpleGrantedAuthority(roleDto.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }


}
