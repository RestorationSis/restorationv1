package com.restorationservice.restorationv1.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private long id;
  @Column(name = "uuid", nullable = false)
  private UUID uuid;
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "second_name", nullable = false)
  private String secondName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "driver_license", nullable = false, unique = true)
  private String driverLicense;

  @Column(name = "birth_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "cellphone", nullable = false, unique = true)
  private String cellphone;

  @Column(name = "telephone", nullable = false)
  private String telephone;

  @Column(name = "street_address", nullable = false)
  private String streetAddress;

  @Column(name = "unit_apartment_suite", nullable = false)
  private String unitApartmentSuite;

  @Column(name = "city", nullable = false)
  private String city;

  @Enumerated(EnumType.STRING)
  private State state;

  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @Column(name = "created_on", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdOn;

  @Column(name = "person_language", nullable = false)
  private Language personLanguage;
}
