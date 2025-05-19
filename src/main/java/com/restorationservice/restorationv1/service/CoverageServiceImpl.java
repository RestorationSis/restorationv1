package com.restorationservice.restorationv1.service;


import com.restorationservice.restorationv1.model.policy.Coverage;
import com.restorationservice.restorationv1.repository.CoverageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoverageServiceImpl implements CoverageService {

  private final CoverageRepository coverageRepository;

  @Transactional
  public Coverage addCoverage(Coverage coverage) {
    return coverageRepository.save(coverage);
  }

  @Transactional
  public boolean removeCoverage(Long id) {
    if (coverageRepository.existsById(id)) {
      coverageRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional
  public Coverage updateCoverage(Coverage coverage) {
    if (coverageRepository.existsById(coverage.getId())) {
      return coverageRepository.save(coverage);
    }
    throw new IllegalArgumentException("Coverage with ID " + coverage.getId() + " not found");
  }

  public Coverage getCoverageById(Long id) {
    return coverageRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Coverage with ID " + id + " not found"));
  }

  public List<Coverage> listAllCoverages() {
    return coverageRepository.findAll();
  }
}