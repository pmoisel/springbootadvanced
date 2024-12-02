package com.nterra.springbootadvanced.config;

import com.nterra.springbootadvanced.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("oauth, jpa")
public class EnhancedOAuth2UserService implements
    OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  public final CrudRepository<User, String> userRepository;
  private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

  @Override
  public CustomOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User user = delegate.loadUser(userRequest);
    List<GrantedAuthority> dbAuthorities = new ArrayList<>();
    String login = user.getAttribute("login");
    Optional<User> optionalUser = userRepository.findById(login);
    optionalUser.ifPresentOrElse(dbUser -> {
      dbAuthorities.add(new SimpleGrantedAuthority("ROLE_"+dbUser.getRole()));
        }
        , () -> userRepository.save(User.builder()
            .email(login).build()));

    return new CustomOAuth2User(user, dbAuthorities);
  }
}
