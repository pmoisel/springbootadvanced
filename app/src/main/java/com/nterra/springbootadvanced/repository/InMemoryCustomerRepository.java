package com.nterra.springbootadvanced.repository;

import com.nterra.springbootadvanced.model.Customer;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCustomerRepository implements CrudRepository<Customer, String> {

  private final Map<String, Customer> customers = new HashMap<>();

  @PostConstruct
  public void init() {
    customers.putAll(Map.of(
        "larry.page@google.com",
        Customer.builder().email("larry.page@google.com").firstName("Larry").lastName("Page")
            .build(),
        "larry.ellison@oracle.com",
        Customer.builder().email("larry.ellison@oracle.com").firstName("Larry").lastName("Ellison")
            .build()
    ));
  }

  @Override
  public Optional<Customer> findById(String email) {
    return Optional.ofNullable(customers.getOrDefault(email, null));
  }

  @Override
  public Iterable<Customer> findAll() {
    return customers.values();
  }

  @Override
  public <S extends Customer> S save(S entity) {
    customers.put(entity.getEmail(), entity);
    return entity;
  }

  @Override
  public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public Iterable<Customer> findAllById(Iterable<String> strings) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public long count() {
    return customers.size();
  }

  @Override
  public void deleteById(String s) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public void delete(Customer entity) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public void deleteAllById(Iterable<? extends String> strings) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Customer> entities) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public void deleteAll() {
    throw new RuntimeException("not implemented");
  }
}
