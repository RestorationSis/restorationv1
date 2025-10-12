package com.restorationservice.restorationv1.service;

import com.restorationservice.restorationv1.mapper.DamageTypeMapper;
import com.restorationservice.restorationv1.model.claim.DamageType;
import com.restorationservice.restorationv1.model.dto.claim.DamageTypeDTO;
import com.restorationservice.restorationv1.repository.DamageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DamageTypeServiceImpl implements DamageTypeService {

  private final DamageTypeRepository damageTypeRepository;
  private final DamageTypeMapper mapper;

  @Transactional
  @Override
  public DamageTypeDTO addDamageType(DamageTypeDTO dto) {
    DamageType entity = mapper.toEntity(dto);
    return mapper.toDTO(damageTypeRepository.save(entity));
  }

  @Transactional
  @Override
  public DamageTypeDTO updateDamageType(Long damageId, DamageTypeDTO dto) {
    DamageType existing = damageTypeRepository.findById(damageId)
        .orElseThrow(() -> new IllegalArgumentException("DamageType with ID " + damageId + " not found"));
    existing.setName(dto.getName());
    existing.setDescription(dto.getDescription());
    return mapper.toDTO(damageTypeRepository.save(existing));
  }

  @Transactional
  @Override
  public boolean removeDamageType(Long damageId) {
    if (damageTypeRepository.existsById(damageId)) {
      damageTypeRepository.deleteById(damageId);
      return true;
    }
    return false;
  }

  @Override
  public DamageTypeDTO getDamageTypeById(Long damageId) {
    DamageType entity = damageTypeRepository.findById(damageId)
        .orElseThrow(() -> new IllegalArgumentException("DamageType with ID " + damageId + " not found"));
    return mapper.toDTO(entity);
  }

  @Override
  public List<DamageTypeDTO> getAllDamageTypes() {
    return damageTypeRepository.findAll().stream()
        .map(mapper::toDTO)
        .collect(Collectors.toList());
  }
}
