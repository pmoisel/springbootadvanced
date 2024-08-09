package com.nterra.springbootadvanced.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.nterra.springbootadvanced.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryCustomerRepositoryTest {

  private InMemoryCustomerRepository inMemoryCustomerRepository;

  @BeforeEach
  public void setUp() {
    inMemoryCustomerRepository = new InMemoryCustomerRepository();
    inMemoryCustomerRepository.init();
  }

  @Test
  public void testAddCustomer() {
    assertThat(inMemoryCustomerRepository.count(), equalTo(2L));

    inMemoryCustomerRepository.save(new Customer("jd@sacredheart.com", "John", "Dorian"));

    assertThat(inMemoryCustomerRepository.count(), equalTo(3L));
  }
}
