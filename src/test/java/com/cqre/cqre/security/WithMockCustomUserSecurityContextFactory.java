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

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .name("test1")
                .studentId("321312312")
                .loginId("loginId")
                .password("password")
                .email("test1@gmail.com")
                .address(new Address("aaa", "aaa"))
                .emailVerified("true")
                .emailCheckToken("b345ecde-b142-4371-af6b-7009af59d050")
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
