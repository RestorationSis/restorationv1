package com.restorationservice.restorationv1.model.customer;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.model.Country;
import com.restorationservice.restorationv1.model.State;
import com.restorationservice.restorationv1.security.SecurityUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private long id;

  @NotNull(message = "streetAddress field must not be null")
  @Column(name = "street_address", nullable = false)
  private String streetAddress;

  @NotNull(message = "unit apartment field must not be null")
  @Column(name = "unit_apartment_suite", nullable = false)
  private String unitApartmentSuite;

  @NotNull(message = "city field must not be null")
  @Column(name = "city", nullable = false)
  private String city;

  @Enumerated(EnumType.STRING)
  private State state;

  @Enumerated(EnumType.STRING)
  private Country country;

  @NotNull(message = "zip code field must not be null")
  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @Column(name = "isPrimary")
  @ColumnDefault("false")
  private Boolean isPrimary;

  @Column(name = "created_by")
  private String createdBy;

  @PrePersist
  private void onCreate() {
    this.createdBy = SecurityUtils.getAuthenticatedUsername();
  }
}
