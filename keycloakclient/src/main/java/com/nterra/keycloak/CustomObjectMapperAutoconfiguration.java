package com.nterra.keycloak;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@AutoConfiguration
public class CustomObjectMapperAutoconfiguration {

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(Feature.ALLOW_COMMENTS);
    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    return objectMapper;
  }
}
