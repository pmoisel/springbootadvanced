package com.nterra.springbootadvanced.service;

import com.nterra.springbootadvanced.model.Customer;
import com.nterra.springbootadvanced.model.CustomerDTO;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  public final CrudRepository<Customer, String> customerRepository;

  public Stream<CustomerDTO> findAll() {
    return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
        .map(CustomerDTO::new);
  }

  public Customer save(CustomerDTO customerDTO) {
    return customerRepository.save(new Customer(customerDTO));
  }
}
