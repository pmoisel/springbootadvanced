package com.nterra.springbootadvanced.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrustedDomainValidator
    implements ConstraintValidator<TrustedDomain, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true;
    }
    return !value.contains("amazon.com");
  }
}
