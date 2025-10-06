package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.model.claim.Claim;
import com.restorationservice.restorationv1.model.claim.ClaimInsuranceAdjuster;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInsuranceAdjusterDTO;

import org.springframework.stereotype.Component;

@Component
public class ClaimInsuranceAdjusterMapper {

  public ClaimInsuranceAdjuster toEntity(ClaimInsuranceAdjusterDTO dto, Claim claim) {
    return ClaimInsuranceAdjuster.builder().claim(claim).fullName(dto.getFullName()).email(dto.getEmail())
        .phoneNumber(dto.getPhoneNumber()).extension(dto.getExtension()).build();
  }

  public ClaimInsuranceAdjusterDTO toDTO(ClaimInsuranceAdjuster entity) {
    ClaimInsuranceAdjusterDTO dto = new ClaimInsuranceAdjusterDTO();
    dto.setClaimId(entity.getClaim().getClaimId());
    dto.setFullName(entity.getFullName());
    dto.setEmail(entity.getEmail());
    dto.setPhoneNumber(entity.getPhoneNumber());
    dto.setExtension(entity.getExtension());
    dto.setInsuranceAdjusterId(entity.getAdjusterId());
    return dto;
  }
}