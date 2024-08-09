package com.nterra.springbootadvanced.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

  private String email;

  private String firstName;

  private String lastName;

  public CustomerDTO(Customer customer) {
    this.email = customer.getEmail();
    this.firstName = customer.getFirstName();
    this.lastName = customer.getLastName();
  }
}
