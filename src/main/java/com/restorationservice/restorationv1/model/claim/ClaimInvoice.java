package com.restorationservice.restorationv1.model.claim;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(EntityChangeLogListener.class)
@Table(name = "claim_invoices")
public class ClaimInvoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "invoice_id", updatable = false, nullable = false)
  private Long invoiceId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "claim_id", referencedColumnName = "claim_id", nullable = false)
  private Claim claim;


  @NotNull
  @Column(name = "invoice_name", nullable = false)
  private String invoiceName;

  @NotNull
  @DecimalMin(value = "0.01", message = "Amount must be positive")
  @Column(name = "amount", precision = 19, scale = 4, nullable = false)
  private BigDecimal amount;

  @NotNull
  @Column(name = "date_issued", nullable = false)
  private Instant dateIssued;

  @NotNull
  @Column(name = "invoice_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ClaimInvoiceStatus status;

  @NotNull
  @Column(name = "analyst_id", nullable = false)
  private Long analystId;


  @Column(name = "link")
  private String link;

  @NotNull
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;
}