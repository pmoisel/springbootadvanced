package com.nterra.springbootadvanced.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TrustedDomainValidatorTest {

  private TrustedDomainValidator validator = new TrustedDomainValidator();

  @ParameterizedTest
  @MethodSource("emailResultProvider")
  public void test(String email, Boolean expected) {
    assertThat(validator.isValid(email, null)).isEqualTo(expected);
  }

  static Stream<Arguments> emailResultProvider() {
    return Stream.of(
        arguments("some@google.com", true),
        arguments("some@amazon.com", false),
        arguments("some@amazon.de", true)
    );
  }

}
