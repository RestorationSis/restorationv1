package com.restorationservice.restorationv1.service;

import java.util.List;

import com.restorationservice.restorationv1.model.claim.Representative;
import com.restorationservice.restorationv1.model.claim.RepresentativeType;

public interface RepresentativeService {
  List<RepresentativeType> getAllRepresentativeCatalog();
  RepresentativeType createRepresentativeCatalog(RepresentativeType newCatalogItem);
  List<Representative> getAllRepresentatives();
  Representative getRepresentativeById(long id);
  Representative createRepresentative(Representative representative);
  Representative updateRepresentative(long id, Representative representative);
  void deleteRepresentative(long id);
}
