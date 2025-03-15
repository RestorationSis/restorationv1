package com.restorationservice.restorationv1.model.policy;

public enum Status {
  ACTIVE("Active"),
  INACTIVE("Inactive");

  private final String displayName;

  Status(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}

