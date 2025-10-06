package com.restorationservice.restorationv1.service;

import com.restorationservice.restorationv1.model.dto.claim.ClaimInsuranceAdjusterDTO;

import java.util.List;

public interface ClaimInsuranceAdjusterService {

  ClaimInsuranceAdjusterDTO addAdjuster(ClaimInsuranceAdjusterDTO dto);

  ClaimInsuranceAdjusterDTO updateAdjuster(Long adjusterId, ClaimInsuranceAdjusterDTO dto);

  boolean removeAdjuster(Long adjusterId);

  ClaimInsuranceAdjusterDTO getAdjusterById(Long adjusterId);
  List<ClaimInsuranceAdjusterDTO> getAllAdjuster();

  List<ClaimInsuranceAdjusterDTO> getAdjustersByClaimId(Long claimId);
}