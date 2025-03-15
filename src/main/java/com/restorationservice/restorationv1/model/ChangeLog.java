package com.restorationservice.restorationv1.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "changelog")
public class ChangeLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String entityName;
  private String user;

  @Column(nullable = false)
  private String action;

  @Column(columnDefinition = "TEXT")
  private String changes;

  @Column(nullable = false)
  private Instant timestamp = Instant.now();

  public ChangeLog() {}

  public ChangeLog(String entityName, String user, String action, String changes) {
    this.entityName = entityName;
    this.action = action;
    this.user = user;
    this.changes = changes;
    this.timestamp = Instant.now();
  }
}
