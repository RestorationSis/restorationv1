package com.restorationservice.restorationv1.service;

import java.util.List;

import com.restorationservice.restorationv1.model.claim.ClaimInvoice;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInvoiceDTO;

public interface ClaimInvoiceService {
  ClaimInvoiceDTO addInvoice(ClaimInvoiceDTO invoice);

  ClaimInvoiceDTO updateInvoice(Long invoiceId, ClaimInvoiceDTO updatedInvoice);

  boolean removeInvoice(Long invoiceId);

  ClaimInvoiceDTO getInvoiceById(Long invoiceId);

  List<ClaimInvoiceDTO> getInvoicesByClaimId(Long claimId);
}
