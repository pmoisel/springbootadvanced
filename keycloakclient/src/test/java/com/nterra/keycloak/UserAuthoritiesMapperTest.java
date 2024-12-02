package com.nterra.keycloak;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

public class UserAuthoritiesMapperTest {

  KeycloakClientAutoconfiguration config = new KeycloakClientAutoconfiguration();
  GrantedAuthoritiesMapper grantedAuthoritiesMapper = config.userAuthoritiesMapper();

  @Test
  public void testUserAuthoritiesMapper() {
    // TODO: JSON einlesen
    Map<String, Object> claims = Map.of("resource_access",
        Map.of("nterra", Map.of("roles", List.of("USER"))));

    OidcIdToken token = new OidcIdToken("a", null, null, Map.of("scope", "openid"));
    OidcUserInfo userInfo = new OidcUserInfo(claims);

    OidcUserAuthority authority = new OidcUserAuthority(token, userInfo);

    Collection<? extends GrantedAuthority> mappedAuthorities = grantedAuthoritiesMapper.mapAuthorities(
        List.of(authority));

    // TODO: generalisieren
    GrantedAuthority next = mappedAuthorities.iterator().next();
    assertThat(next.getAuthority(), equalTo("ROLE_USER"));

  }

}
