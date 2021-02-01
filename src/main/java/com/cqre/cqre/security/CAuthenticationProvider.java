package com.cqre.cqre.security;

import com.cqre.cqre.exception.customexception.CEmailTokenFalseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CUserDetailsService cUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    public CAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserContext userContext = (UserContext) cUserDetailsService.loadUserByUsername(loginId);

        if (!passwordEncoder.matches(password, userContext.getUser().getPassword())) {
            throw new BadCredentialsException("");
        }else if (userContext.getUser().getEmailVerified().equals("false")) {
            throw new CEmailTokenFalseException("");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userContext.getUser(), null, userContext.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
