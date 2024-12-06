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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "first name field must not be null")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "second_name", nullable = false)
  private String secondName;

  @NotNull(message = "last name field must not be null")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotNull(message = "driver license field must not be null")
  @Column(name = "driver_license", nullable = false, unique = true)
  private String driverLicense;

  @NotNull(message = "birth date field must not be null")
  @Column(name = "birth_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @NotNull(message = "email field must not be null")
  @Email(message = "Email should be valid")
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotNull(message = "cell phone field must not be null")
  @Column(name = "cellphone", nullable = false, unique = true)
  private String cellphone;

  @Column(name = "telephone", nullable = false)
  private String telephone;

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

  @NotNull(message = "zip code field must not be null")
  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @Column(name = "created_on", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdOn;

  @NotNull(message = "language field must not be null")
  @Column(name = "person_language", nullable = false)
  private Language personLanguage;
}
