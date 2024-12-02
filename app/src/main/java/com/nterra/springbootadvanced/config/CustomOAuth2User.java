package com.nterra.springbootadvanced.config;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
@Profile("oauth")
public class CustomOAuth2User implements OAuth2User {

  private final OAuth2User oauth2User;
  private final Collection<GrantedAuthority> dbAuthorities;

  @Override
  public Map<String, Object> getAttributes() {
    return oauth2User.getAttributes();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Stream.concat(
            oauth2User.getAuthorities().stream(),
            dbAuthorities.stream())
        .collect(Collectors.toList());
  }

  @Override
  public String getName() {
    return oauth2User.getAttribute("login");
  }
}
