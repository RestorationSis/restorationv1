package com.restorationservice.restorationv1.model.customer;

public enum CustomerStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE"),
  SUSPENDED("SUSPENDED");
  private final String status;

  CustomerStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
