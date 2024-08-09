package com.nterra.springbootadvanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  @Id
  private String email;
  private String firstName;
  private String lastName;

  public Customer(CustomerDTO customerDTO) {
    this(customerDTO.getEmail(), customerDTO.getFirstName(), customerDTO.getLastName());
  }
}
