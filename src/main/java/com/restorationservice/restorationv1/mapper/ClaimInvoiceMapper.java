package com.restorationservice.restorationv1.mapper;


import com.restorationservice.restorationv1.model.claim.Claim;
import com.restorationservice.restorationv1.model.claim.ClaimInvoice;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInvoiceDTO;

import org.springframework.stereotype.Component;

@Component
public class ClaimInvoiceMapper {

  public ClaimInvoiceDTO toDTO(ClaimInvoice claimInvoice) {
    if (claimInvoice == null) {
      return null;
    }

    ClaimInvoiceDTO dto = new ClaimInvoiceDTO();
    dto.setClaimId(claimInvoice.getClaim() != null ? claimInvoice.getClaim().getClaimId() : null);
    dto.setInvoiceName(claimInvoice.getInvoiceName());
    dto.setAmount(claimInvoice.getAmount());
    dto.setStatus(claimInvoice.getStatus());
    dto.setAnalystId(claimInvoice.getAnalystId());
    dto.setLink(claimInvoice.getLink());
    return dto;
  }

  public ClaimInvoice toEntity(ClaimInvoiceDTO dto, Claim claim) {
    if (dto == null) {
      return null;
    }

    return ClaimInvoice.builder()
        .claim(claim)
        .invoiceName(dto.getInvoiceName())
        .amount(dto.getAmount())
        .status(dto.getStatus())
        .analystId(dto.getAnalystId())
        .link(dto.getLink())
        .build();
  }
}
