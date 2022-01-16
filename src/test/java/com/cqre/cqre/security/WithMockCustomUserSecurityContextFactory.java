package com.cqre.cqre.security;

import com.cqre.cqre.domain.user.Address;
import com.cqre.cqre.domain.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .name("user")
                .studentId("11111111")
                .loginId("loginId")
                .password("password")
                .email("email@email.com")
                .address(new Address("street", "detail"))
                .emailVerified("true")
                .emailCheckToken(UUID.randomUUID().toString())
                .role("ROLE_USER")
                .build();

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole()));

        UserContext userContext = new UserContext(user, roles);
        Authentication auth = new UsernamePasswordAuthenticationToken(userContext.getUser(), null, userContext.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}
