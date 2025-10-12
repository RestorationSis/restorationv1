package com.restorationservice.restorationv1.service;

import com.restorationservice.restorationv1.model.dto.claim.DamageTypeDTO;
import java.util.List;

public interface DamageTypeService {

  DamageTypeDTO addDamageType(DamageTypeDTO dto);

  DamageTypeDTO updateDamageType(Long damageId, DamageTypeDTO dto);

  boolean removeDamageType(Long damageId);

  DamageTypeDTO getDamageTypeById(Long damageId);

  List<DamageTypeDTO> getAllDamageTypes();
}
