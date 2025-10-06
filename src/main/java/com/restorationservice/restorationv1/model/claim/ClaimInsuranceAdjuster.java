package com.restorationservice.restorationv1.model.claim;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(EntityChangeLogListener.class)
@Table(name = "claim_insurance_adjusters")
public class ClaimInsuranceAdjuster {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "adjuster_id", updatable = false, nullable = false)
  private Long adjusterId;
  @NotNull
  @ManyToOne
  @JoinColumn(name = "claim_id", referencedColumnName = "claim_id", nullable = false)
  private Claim claim;
  @NotNull
  @Column(name = "full_name", nullable = false)
  private String fullName;
  @NotNull
  @Email
  @Column(name = "email", nullable = false, unique = false)
  private String email;
  @NotNull
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;
  @Column(name = "extension")
  private String extension;
  @NotNull
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
  @Column(name = "updated_at")
  private Instant updatedAt;
}