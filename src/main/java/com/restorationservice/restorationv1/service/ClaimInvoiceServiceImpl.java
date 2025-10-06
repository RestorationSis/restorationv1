package com.restorationservice.restorationv1.service;


import com.restorationservice.restorationv1.mapper.ClaimInvoiceMapper;
import com.restorationservice.restorationv1.model.claim.Claim;
import com.restorationservice.restorationv1.model.claim.ClaimInvoice;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInvoiceDTO;
import com.restorationservice.restorationv1.repository.ClaimInvoiceRepository;
import com.restorationservice.restorationv1.repository.ClaimRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ClaimInvoiceServiceImpl implements ClaimInvoiceService {

  private final ClaimInvoiceRepository claimInvoiceRepository;
  private final ClaimRepository claimRepository;
  private final ClaimInvoiceMapper claimInvoiceMapper;

  @Transactional
  @Override
  public ClaimInvoiceDTO addInvoice(ClaimInvoiceDTO invoiceDto) {
    Claim claim = claimRepository.findById(invoiceDto.getClaimId())
        .orElseThrow(() -> new EntityNotFoundException("Claim with ID " + invoiceDto.getClaimId() + " not found"));

    ClaimInvoice invoice = claimInvoiceMapper.toEntity(invoiceDto, claim);
    invoice.setCreatedAt(Instant.now());
    invoice.setUpdatedAt(Instant.now());
    invoice.setDateIssued(Instant.now());

    return claimInvoiceMapper.toDTO(claimInvoiceRepository.save(invoice));
  }

  @Transactional
  @Override
  public boolean removeInvoice(Long invoiceId) {
    if (claimInvoiceRepository.existsById(invoiceId)) {
      claimInvoiceRepository.deleteById(invoiceId);
      return true;
    }
    return false;
  }

  @Transactional
  @Override
  public ClaimInvoiceDTO updateInvoice(Long invoiceId, ClaimInvoiceDTO updatedDto) {
    ClaimInvoice existingInvoice = claimInvoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new IllegalArgumentException("Invoice with ID " + invoiceId + " not found"));

    existingInvoice.setInvoiceName(updatedDto.getInvoiceName());
    existingInvoice.setAmount(updatedDto.getAmount());
    existingInvoice.setStatus(updatedDto.getStatus());
    existingInvoice.setAnalystId(updatedDto.getAnalystId());
    existingInvoice.setLink(updatedDto.getLink());
    existingInvoice.setUpdatedAt(Instant.now());

    return claimInvoiceMapper.toDTO(claimInvoiceRepository.save(existingInvoice));
  }

  @Override
  public ClaimInvoiceDTO getInvoiceById(Long invoiceId) {
    Optional<ClaimInvoice> invoice = Optional.ofNullable(claimInvoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new IllegalArgumentException("Invoice with ID " + invoiceId + " not found")));
    return claimInvoiceMapper.toDTO(invoice.get());
  }

  @Override
  public List<ClaimInvoiceDTO> getInvoicesByClaimId(Long claimId) {
    return claimInvoiceRepository.findByClaim_ClaimId(claimId)
        .stream()
        .map(claimInvoiceMapper::toDTO)
        .collect(Collectors.toList());
  }
}
