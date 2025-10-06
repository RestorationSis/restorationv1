package com.restorationservice.restorationv1.model.customer;

public enum CustomerTags {

  HARD_TO_REACH("HARD_TO_REACH"),
  PREVIOUS_CLAIM("PREVIOUS_CLAIM"),
  INITIALLY_REFUSED_TO_SIGN("INITIALLY_REFUSED_TO_SIGN");
  private final String tag;

  CustomerTags(String status) {
    this.tag = status;
  }

  public String getTags() {
    return tag;
  }
}
