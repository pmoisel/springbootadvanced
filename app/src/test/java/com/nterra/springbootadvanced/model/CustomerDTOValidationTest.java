package com.nterra.springbootadvanced.model;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Locale;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(SpringExtension.class)
@Import({LocalValidatorFactoryBean.class})
public class CustomerDTOValidationTest {

  @Autowired
  private Validator validator;

  @BeforeEach
  public void setUp() {
    Locale.setDefault(Locale.GERMAN);
  }

  @Test
  public void testFistName() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("First Name");
    customerDTO.setLastName("Last Name");
    customerDTO.setEmail("email@email.com");
    Set<ConstraintViolation<CustomerDTO>> validate = validator.validate(customerDTO);

    assertThat(validate).isEmpty();
  }

  @Test
  public void testFistNameViolation() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("");
    customerDTO.setLastName("Last Name");
    customerDTO.setEmail("email@email.com");
    Set<ConstraintViolation<CustomerDTO>> validate = validator.validate(customerDTO);

    assertThat(validate).hasSize(1);
    assertThat(validate).extracting(ConstraintViolation::getMessage)
        .contains("Größe muss zwischen 1 und 40 sein");
  }

  @Test
  public void testLastNameViolation() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("First Name");
    customerDTO.setLastName("");
    customerDTO.setEmail("email@email.com");
    Set<ConstraintViolation<CustomerDTO>> validate = validator.validate(customerDTO);

    assertThat(validate).hasSize(1);
    assertThat(validate).extracting(ConstraintViolation::getMessage)
        .contains("Größe muss zwischen 1 und 40 sein");
  }

  @Test
  public void testEmailViolation() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("First Name");
    customerDTO.setLastName("Last Name");
    customerDTO.setEmail("email");
    Set<ConstraintViolation<CustomerDTO>> validate = validator.validate(customerDTO);

    assertThat(validate).hasSize(1);
    assertThat(validate).extracting(ConstraintViolation::getMessage)
        .contains("muss eine korrekt formatierte E-Mail-Adresse sein");
  }

  @Test
  public void testViolations() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("");
    customerDTO.setLastName("Last Name is waaaaaaayyyyy toooooooooooooooo long");
    customerDTO.setEmail("email");
    Set<ConstraintViolation<CustomerDTO>> validate = validator.validate(customerDTO);

    assertThat(validate).hasSize(3);
    assertThat(validate).extracting(ConstraintViolation::getMessage)
        .containsExactlyInAnyOrder(
            "Größe muss zwischen 1 und 40 sein",
            "Größe muss zwischen 1 und 40 sein",
            "muss eine korrekt formatierte E-Mail-Adresse sein");
  }

  @Test
  public void testTrustedDomainViolation() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstName("First Name");
    customerDTO.setLastName("Last Name");
    customerDTO.setEmail("email@amazon.com");
    Set<ConstraintViolation<CustomerDTO>> validate = validator.validate(customerDTO);

    assertThat(validate).hasSize(1);
    assertThat(validate).extracting(ConstraintViolation::getMessage)
        .contains("{email.domain}");
  }

}
