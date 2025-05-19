package com.restorationservice.restorationv1.model.customer;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.json.Views;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.security.SecurityUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  @JsonView(Views.Summary.class)
  private long id;

  @NotNull(message = "streetAddress field must not be null")
  @Column(name = "street_address", nullable = false)
  @JsonView(Views.Summary.class)
  private String streetAddress;

  @NotNull(message = "unit apartment field must not be null")
  @Column(name = "unit_apartment_suite", nullable = false)
  @JsonView(Views.Summary.class)
  private String unitApartmentSuite;

  @NotNull(message = "city field must not be null")
  @Column(name = "city", nullable = false)
  @JsonView(Views.Summary.class)
  private String city;

  @Enumerated(EnumType.STRING)
  @JsonView(Views.Summary.class)
  private State state;

  @Enumerated(EnumType.STRING)
  @JsonView(Views.Summary.class)
  private Country country;

  @NotNull(message = "zip code field must not be null")
  @Column(name = "zip_code", nullable = false)
  @JsonView(Views.Summary.class)
  private String zipCode;

  @Column(name = "isPrimary")
  @ColumnDefault("false")
  @JsonView(Views.Summary.class)
  private Boolean isPrimary;

  @Column(name = "created_by")
  @JsonView(Views.Summary.class)
  private String createdBy;

  @ManyToOne // Use ManyToOne to link to the Policy entity
  @JoinColumn(name = "policy_id", unique = true) // Specify the foreign key column and uniqueness
  @JsonView(Views.Summary.class)
  private Policy policy;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  @JsonView(Views.Summary.class)
  private AddressStatus status;

  @PrePersist
  private void onCreate() {
    this.createdBy = SecurityUtils.getAuthenticatedUsername();
    this.status = AddressStatus.ACTIVE;
  }
}
