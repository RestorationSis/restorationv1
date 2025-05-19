package com.restorationservice.restorationv1.service;

import java.util.List;

import com.restorationservice.restorationv1.model.policy.Coverage;

public interface CoverageService {
  Coverage addCoverage(Coverage coverage);

  boolean removeCoverage(Long id);

  Coverage updateCoverage(Coverage coverage);

  Coverage getCoverageById(Long id);

  List<Coverage> listAllCoverages();
}
