package com.nterra.springbootadvanced.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    return new InMemoryUserDetailsManager(
        User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build(),
        User.withDefaultPasswordEncoder()
            .username("manager")
            .password("password")
            .roles("MANAGER")
            .build());
  }

  @Bean
  @Profile("!oauth")
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(Customizer.withDefaults());
    http.logout(logoutConfigurer -> logoutConfigurer
        .logoutSuccessUrl("/index"));
    return http.build();
  }

  @Bean
  @Profile("oauth")
  protected SecurityFilterChain filterChainOAuth(HttpSecurity http) throws Exception {
    http.oauth2Login(Customizer.withDefaults());
    http.logout(logoutConfigurer -> logoutConfigurer
        .logoutSuccessUrl("/index?loggedout"));
    return http.build();
  }


  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy);
    return expressionHandler;
  }

  @Bean
  static RoleHierarchy roleHierarchy() {
    return RoleHierarchyImpl.withDefaultRolePrefix()
        .role("MANAGER").implies("USER")
        .build();
  }

}
