package com.nterra.springbootadvanced.beans;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Anredenzeile {

  private final String text;

  public String erschaffe(String name) {
    return text + " " + name;
  }
}
