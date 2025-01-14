package com.restorationservice.restorationv1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
