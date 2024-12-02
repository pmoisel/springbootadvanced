package com.nterra.springbootadvanced.beans;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(BeanProvider.class)
public class BeanTest {

  @Autowired
  private Anredenzeile anredenzeile;

  @Autowired
  @Qualifier("hessen")
  private Anredenzeile anredenzeile2;

  @Autowired
  private Map<String, Anredenzeile> anredenzeilen;

  @Test
  public void testPrimary() {
    String zeile = anredenzeile.erschaffe("Meier");
    assertThat(zeile).isEqualTo("Sehr geehrte(r) Meier");
  }

  @Test
  public void testHessen() {
    String zeile = anredenzeile2.erschaffe("Meier");
    assertThat(zeile).isEqualTo("Ei Gude Meier");
  }

  @Test
  public void mehrere() {
    assertThat(anredenzeilen.size()).isEqualTo(2);
  }
}
