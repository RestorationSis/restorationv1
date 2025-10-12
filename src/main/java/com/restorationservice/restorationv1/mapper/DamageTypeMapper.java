package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.model.claim.DamageType;
import com.restorationservice.restorationv1.model.dto.claim.DamageTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class DamageTypeMapper {

  public DamageType toEntity(DamageTypeDTO dto) {
    return DamageType.builder()
        .damageId(dto.getDamageId())
        .name(dto.getName())
        .description(dto.getDescription())
        .build();
  }

  public DamageTypeDTO toDTO(DamageType entity) {
    DamageTypeDTO dto = new DamageTypeDTO();
    dto.setDamageId(entity.getDamageId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    return dto;
  }
}
