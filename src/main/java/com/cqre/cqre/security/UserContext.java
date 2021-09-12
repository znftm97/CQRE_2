package com.cqre.cqre.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final com.cqre.cqre.domain.User user;

    public com.cqre.cqre.domain.User getUser(){
        return user;
    }

    public UserContext(com.cqre.cqre.domain.User user, Collection<? extends GrantedAuthority> authorities){
        super(user.getLoginId(), user.getPassword(), authorities);
        this.user = user;
    }
}
