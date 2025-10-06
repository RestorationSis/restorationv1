package com.restorationservice.restorationv1.model.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(
    name = "insurance_company",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "Legal_name_insurance"),
        @UniqueConstraint(columnNames = "Commercial_name")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceCompany implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id_Insurance_Company", updatable = false, nullable = false)
  private Long id;

  @Column(name = "Legal_name_insurance", unique = true, nullable = false, length = 255)
  private String legalNameInsurance;

  @Column(name = "Commercial_name", unique = true, nullable = false, length = 255)
  private String commercialName;

  @JsonIgnore
  @Column(name = "Telephone_1", length = 20)
  private String telephone1;

  @JsonIgnore
  @Column(name = "Telephone_2", length = 20)
  private String telephone2;

  @Column(name = "Email_1", length = 255)
  private String email1;

  @JsonIgnore
  @Column(name = "Email_2", length = 255)
  private String email2;

  @Column(name = "Cellphone_1", length = 20)
  private String cellphone1;

  @Column(name = "Cellphone_2", length = 20)
  private String cellphone2;

  @Column(name = "Website", length = 255)
  private String website;
}
