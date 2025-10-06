package com.restorationservice.restorationv1.service;

import com.restorationservice.restorationv1.mapper.ClaimInsuranceAdjusterMapper;
import com.restorationservice.restorationv1.model.claim.Claim;
import com.restorationservice.restorationv1.model.claim.ClaimInsuranceAdjuster;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInsuranceAdjusterDTO;
import com.restorationservice.restorationv1.repository.ClaimInsuranceAdjusterRepository;
import com.restorationservice.restorationv1.repository.ClaimRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimInsuranceAdjusterServiceImpl implements ClaimInsuranceAdjusterService {

  private final ClaimInsuranceAdjusterRepository adjusterRepository;
  private final ClaimRepository claimRepository;
  private final ClaimInsuranceAdjusterMapper mapper;

  @Transactional
  @Override
  public ClaimInsuranceAdjusterDTO addAdjuster(ClaimInsuranceAdjusterDTO dto) {
    Claim claim = claimRepository.findById(dto.getClaimId())
        .orElseThrow(() -> new IllegalArgumentException("Claim with ID " + dto.getClaimId() + " not found"));
    ClaimInsuranceAdjuster adjuster = mapper.toEntity(dto, claim);
    adjuster.setCreatedAt(Instant.now());
    adjuster.setUpdatedAt(Instant.now());
    return mapper.toDTO(adjusterRepository.save(adjuster));
  }

  @Transactional
  @Override
  public ClaimInsuranceAdjusterDTO updateAdjuster(Long adjusterId, ClaimInsuranceAdjusterDTO dto) {
    ClaimInsuranceAdjuster existing = adjusterRepository.findById(adjusterId)
        .orElseThrow(() -> new IllegalArgumentException("Adjuster with ID " + adjusterId + " not found"));
    existing.setFullName(dto.getFullName());
    existing.setEmail(dto.getEmail());
    existing.setPhoneNumber(dto.getPhoneNumber());
    existing.setExtension(dto.getExtension());
    existing.setUpdatedAt(Instant.now());
    return mapper.toDTO(adjusterRepository.save(existing));
  }

  @Transactional
  @Override
  public boolean removeAdjuster(Long adjusterId) {
    if (adjusterRepository.existsById(adjusterId)) {
      adjusterRepository.deleteById(adjusterId);
      return true;
    }
    return false;
  }

  @Override
  public ClaimInsuranceAdjusterDTO getAdjusterById(Long adjusterId) {
    ClaimInsuranceAdjuster adjuster = adjusterRepository.findById(adjusterId)
        .orElseThrow(() -> new IllegalArgumentException("Adjuster with ID " + adjusterId + " not found"));
    return mapper.toDTO(adjuster);
  }

  @Override
  public List<ClaimInsuranceAdjusterDTO> getAllAdjuster() {
    return adjusterRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());

  }

  @Override
  public List<ClaimInsuranceAdjusterDTO> getAdjustersByClaimId(Long claimId) {
    return adjusterRepository.findByClaim_ClaimId(claimId).stream().map(mapper::toDTO).collect(Collectors.toList());
  }
}
