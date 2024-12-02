package com.nterra.springbootadvanced.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class BeanProvider {

  @Bean
  @Primary
  public Anredenzeile formal() {
    return new Anredenzeile("Sehr geehrte(r)");
  }

  @Bean
  public Anredenzeile hessen() {
    return new Anredenzeile("Ei Gude");
  }

}
