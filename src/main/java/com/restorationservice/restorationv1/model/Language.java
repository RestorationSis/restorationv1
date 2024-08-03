package com.restorationservice.restorationv1.model;

import lombok.Getter;

@Getter
public enum Language {
  ENGLISH("EN", "English"),
  SPANISH("ES", "Spanish");

  private final String code;
  private final String language;

  Language(String code, String language) {
    this.code = code;
    this.language = language;
  }
}
