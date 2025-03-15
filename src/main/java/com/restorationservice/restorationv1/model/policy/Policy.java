package com.restorationservice.restorationv1.model.policy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

import com.restorationservice.restorationv1.model.customer.Address;

@Entity
@Table(name = "policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id_policy", nullable = false, updatable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "address_fk", referencedColumnName = "id")
  private Address address;

  @ManyToOne
  @JoinColumn(name = "insurance_company_fk", nullable = false, referencedColumnName = "Id_Insurance_Company")
  private InsuranceCompany insuranceCompany;

  @ManyToOne
  @JoinColumn(name = "policy_type_fk", referencedColumnName = "Id_Type", nullable = false)
  private PolicyType policyType;

  @ManyToOne
  @JoinColumn(name = "coverage_fk", referencedColumnName = "Id_coverage", nullable = false)
  private Coverage coverage;

  @Column(name = "policy_number", nullable = false, length = 50, unique = true)
  private String policyNumber;

  @Column(name = "coverage_limit", nullable = false, length = 50)
  private String coverageLimit;

  @Column(name = "from_date", nullable = false)
  private LocalDate fromDate;

  @Column(name = "expiration_date", nullable = false)
  private LocalDate expirationDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
  private Status status = Status.ACTIVE;
}
