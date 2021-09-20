package com.cqre.cqre.security;

import com.cqre.cqre.exceptionhandler.CLoginFailHandler;
import com.cqre.cqre.security.OAuth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CAuthenticationProvider(passwordEncoder());
    }

    private final CLoginFailHandler cLoginFailHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CUserDetailsService cUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/post/createNoticePost", "/createCoupon", "/couponList").hasRole("ADMIN")
                .antMatchers("/history", "/board/noticeBoard", "/board/freeBoard", "/gallery", "/shop").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll();
        http
                .csrf();
        http
                .formLogin()
                .loginPage("/user/sign")
                .loginProcessingUrl("/user/signIn")
                .defaultSuccessUrl("/home")
                .failureHandler(cLoginFailHandler)
                .permitAll();
        http
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/sign")
                .permitAll();
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        http
                .rememberMe()
                .userDetailsService(cUserDetailsService)
                .tokenValiditySeconds(604800);
        http
                .exceptionHandling().accessDeniedPage("/authorityException");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
