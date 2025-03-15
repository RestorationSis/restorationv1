package com.restorationservice.restorationv1.component;

import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restorationservice.restorationv1.model.customer.Customer;

@Component
public class EntityListener {

  private static SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  public void setSequenceGeneratorService(SequenceGeneratorService service) {
    sequenceGeneratorService = service;
  }

  @PrePersist
  public void assignSequence(Object entity) {
    if (entity instanceof Customer customer && customer.getNumber() == null) {
      customer.setNumber(sequenceGeneratorService.getNextSequenceValue());
    }
  }
}
