package com.nterra.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nterra.spring.security.oauth2.keycloakclient")
public class KeycloakClientProperties {

  private String registrationId = "keycloak";
  private String clientId;
  private String clientSecret;
  private String uri;
  private String realm;
  private String redirectUri;

  public String getRegistrationId() {
    return registrationId;
  }

  public void setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getRealm() {
    return realm;
  }

  public void setRealm(String realm) {
    this.realm = realm;
  }

  public String getRedirectUri() {
    return redirectUri;
  }

  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }
}
