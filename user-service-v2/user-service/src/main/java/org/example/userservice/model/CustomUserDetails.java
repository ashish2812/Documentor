package org.example.userservice.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CustomUserDetails extends UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserDetails byUsername) {
        this.username = byUsername.getUserName();
        this.password= byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();
        log.debug("adding user role: {} for username: {}",byUsername.getRole().getName(),username);
        auths.add(new SimpleGrantedAuthority(byUsername.getRole().getName().toUpperCase()));
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}