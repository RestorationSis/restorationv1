package com.restorationservice.restorationv1.model.customer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.json.Views;
import com.restorationservice.restorationv1.security.SecurityUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(EntityChangeLogListener.class)
@Table(name="notes")
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  @JsonView(Views.Summary.class)
  private long id;

  @Column(name = "created_on", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @JsonView(Views.Summary.class)
  private Date createdOn;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  @JsonView(Views.Summary.class)
  private Date lastUpdate;

  @NotNull(message = "author cannot be null")
  @Column(name = "author", nullable = false)
  @JsonView(Views.Summary.class)
  private String author;

  @NotNull(message = "content cannot be null")
  @Column(name = "content", nullable = false)
  @JsonView(Views.Summary.class)
  private String content;

  @Column(name = "created_by")
  @JsonView(Views.Summary.class)
  private String createdBy;

  @PrePersist
  private void onCreate() {
    this.createdOn = new Date();
    this.createdBy = SecurityUtils.getAuthenticatedUsername();
    this.author = SecurityUtils.getAuthenticatedFullName();
  }
}
