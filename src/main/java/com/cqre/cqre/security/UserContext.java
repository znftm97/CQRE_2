package com.cqre.cqre.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final com.cqre.cqre.entity.User user;

    public com.cqre.cqre.entity.User getUser(){
        return user;
    }

    public UserContext(com.cqre.cqre.entity.User user, Collection<? extends GrantedAuthority> authorities){
        super(user.getLoginId(), user.getPassword(), authorities);
        this.user = user;
    }
}
