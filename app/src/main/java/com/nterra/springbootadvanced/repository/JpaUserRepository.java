package com.nterra.springbootadvanced.repository;

import com.nterra.springbootadvanced.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface JpaUserRepository extends CrudRepository<User, String> {

}
