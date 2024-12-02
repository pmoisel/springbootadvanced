package com.nterra.keycloak;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

@AutoConfiguration
@EnableConfigurationProperties(KeycloakClientProperties.class)
public class KeycloakClientAutoconfiguration {

  @Bean
  public ClientRegistration keycloakClientRegistration(KeycloakClientProperties properties) {

    String uriWithRealm = String.format("%s/realms/%s", properties.getUri(),
        properties.getRealm());

    ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(properties.getRegistrationId());
    builder.clientId(properties.getClientId());
    builder.clientSecret(properties.getClientSecret());
    builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
    builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
    builder.scope("openid");
    builder.authorizationUri(uriWithRealm + "/protocol/openid-connect/auth");
    builder.tokenUri(uriWithRealm + "/protocol/openid-connect/token");
    builder.jwkSetUri(uriWithRealm + "/protocol/openid-connect/certs");
    builder.userInfoUri(uriWithRealm + "/protocol/openid-connect/userinfo");
    builder.redirectUri(properties.getRedirectUri() + "/login/oauth2/code/keycloak");

    builder.issuerUri(properties.getUri() + "/realms/schulung");
    builder.userNameAttributeName("preferred_username");

    return builder.build();
  }

  @Bean
  public InMemoryClientRegistrationRepository clientRegistrationRepository(
      ClientRegistration keycloakClientRegistration) {
    return new InMemoryClientRegistrationRepository(keycloakClientRegistration);
  }

  @Bean
  public Customizer<OAuth2LoginConfigurer<HttpSecurity>> oAuth2LoginConfigurerCustomizer(
      GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak) {
    return oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo
        .userAuthoritiesMapper(userAuthoritiesMapperForKeycloak));
  }

  @Bean
  @SuppressWarnings("unchecked")
  public GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return authorities -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
      GrantedAuthority authority = authorities.iterator().next();
      boolean isOidc = authority instanceof OidcUserAuthority;

      if (isOidc) {
        OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
        OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

        if (userInfo.hasClaim("resource_access")) {
          Map<String, Object> resourceAccess = userInfo.getClaimAsMap("resource_access");
          Map<String, Collection<String>> clientId = (Map<String, Collection<String>>) resourceAccess.get(
              "nterra");
          Collection<String> roles = clientId.get("roles");
          mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
        }
      } else {
        OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
        Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

        if (userAttributes.containsKey("resource_access")) {
          Map<String, Object> resourceAccess = (Map<String, Object>) userAttributes.get(
              "resource_access");
          Map<String, Collection<String>> clientId = (Map<String, Collection<String>>) resourceAccess.get(
              "nterra");
          Collection<String> roles = clientId.get("roles");
          mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
        }
      }
      return mappedAuthorities;
    };
  }

  Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(
        Collectors.toList());
  }
}
