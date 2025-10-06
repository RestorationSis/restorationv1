package com.restorationservice.restorationv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restorationservice.restorationv1.model.claim.Representative;
import com.restorationservice.restorationv1.model.claim.RepresentativeType;
import com.restorationservice.restorationv1.repository.RepresentativeCatalogRepository;
import com.restorationservice.restorationv1.repository.RepresentativeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepresentativeServiceImpl implements RepresentativeService {

  private final RepresentativeCatalogRepository representativeCatalogRepository;
  private final RepresentativeRepository representativeRepository;

  @Override
  public List<RepresentativeType> getAllRepresentativeCatalog() {
    return representativeCatalogRepository.findAll();
  }

  @Override
  public RepresentativeType createRepresentativeCatalog(RepresentativeType newCatalogItem) {
    return representativeCatalogRepository.save(newCatalogItem);
  }

  @Override
  public List<Representative> getAllRepresentatives() {
    return representativeRepository.findAll();
  }

  @Override
  public Representative getRepresentativeById(long id) {
    Optional<Representative> representative = representativeRepository.findById(id);
    return representative.orElse(null);
  }

  @Override
  @Transactional
  public Representative createRepresentative(Representative representative) {
    return representativeRepository.save(representative);
  }

  @Override
  @Transactional
  public Representative updateRepresentative(long id, Representative representative) {
    if (representativeRepository.existsById(id)) {
      representative.setId(id);
      return representativeRepository.save(representative);
    } else {
      return null;
    }
  }

  @Override
  @Transactional
  public void deleteRepresentative(long id) {
    representativeRepository.deleteById(id);
  }
}
