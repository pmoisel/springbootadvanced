package com.nterra.springbootadvanced.autoconfig;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nterra.springbootadvanced.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AutoconfigTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testCustomMapper() throws JsonProcessingException {
    CustomerDTO customer = objectMapper.readValue("""
        {
          "email" : "custom@mapper.com",
          "firstName" : "Custom",
          "lastName" : "Mapper",
          "unknown": "property"
        }""", CustomerDTO.class);

    assertThat(customer).extracting(CustomerDTO::getEmail).isEqualTo("custom@mapper.com");
  }
}
