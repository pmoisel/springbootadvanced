package com.nterra.springbootadvanced.model;

import com.nterra.springbootadvanced.validator.TrustedDomain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {


  @Email
  @TrustedDomain
  private String email;

  @Size(min = 1, max = 40)
  private String firstName;

  @Size(min = 1, max = 40)
  private String lastName;

  public CustomerDTO(Customer customer) {
    this.email = customer.getEmail();
    this.firstName = customer.getFirstName();
    this.lastName = customer.getLastName();
  }
}
