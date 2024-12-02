package com.nterra.springbootadvanced.repository;

import com.nterra.springbootadvanced.model.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public interface JpaCustomerRepository extends CrudRepository<Customer, String> {

}
