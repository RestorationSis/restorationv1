package com.restorationservice.restorationv1.model.dto.claim;

import java.math.BigDecimal;

import com.restorationservice.restorationv1.model.claim.ClaimInvoiceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimInvoiceDTO {

  private Long claimId;
  private String invoiceName;
  private BigDecimal amount;
  private ClaimInvoiceStatus status;
  private Long analystId;
  private String link;

}
