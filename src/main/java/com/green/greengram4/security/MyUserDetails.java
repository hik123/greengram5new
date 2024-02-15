package com.green.greengram4.security;

import com.green.greengram4.user.model.UserModel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {

    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;
    private UserModel userModel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return userModel == null ? null : userModel.getUid(); }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
