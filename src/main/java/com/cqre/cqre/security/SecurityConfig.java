package com.cqre.cqre.security;

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
                .antMatchers("/posts/notice-board/page", "/coupons/page", "/coupons").hasRole("ADMIN")
                .antMatchers("/history", "/boards/notice-board", "/boards/free-board", "/gallerys", "/items").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll();
        http
                .csrf();
        http
                .formLogin()
                .loginPage("/users/sign")
                .loginProcessingUrl("/users/sign-in")
                .defaultSuccessUrl("/home")
                .failureHandler(cLoginFailHandler)
                .permitAll();
        http
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/users/sign")
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
