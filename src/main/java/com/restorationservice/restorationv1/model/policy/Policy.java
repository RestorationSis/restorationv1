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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

  @ManyToMany
  @JoinTable(
      name = "policy_coverage",
      joinColumns = @JoinColumn(name = "policy_fk"),
      inverseJoinColumns = @JoinColumn(name = "coverage_fk", referencedColumnName = "Id_coverage")
  )
  private List<Coverage> coverages;

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
  @Column(name = "policy_holder", nullable = false)
  private String policyHolder;
}
