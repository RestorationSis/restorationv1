package com.restorationservice.restorationv1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.json.Views;
import com.restorationservice.restorationv1.model.claim.Representative;
import com.restorationservice.restorationv1.model.claim.RepresentativeType;
import com.restorationservice.restorationv1.service.RepresentativeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/claims")
@RequiredArgsConstructor
public class RepresentativeController {

  private final RepresentativeService representativeService;

  // Existing methods
  @GetMapping("/representativeCatalog")
  @JsonView(Views.Detail.class)
  public ResponseEntity<List<RepresentativeType>>  getAllRepresentativeCatalog() {
    List<RepresentativeType> catalog = representativeService.getAllRepresentativeCatalog();
    if (catalog != null) {
      return ResponseEntity.ok(catalog);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/representativeCatalog")
  @JsonView(Views.Detail.class)
  public ResponseEntity<RepresentativeType> createRepresentativeCatalog(@RequestBody RepresentativeType newCatalogItem) {

    RepresentativeType savedItem = representativeService.createRepresentativeCatalog(newCatalogItem);
    return ResponseEntity.ok(savedItem);
  }

  // New CRUD methods for Representative

  /**
   * Retrieves all representatives.
   * @return A list of all representatives.
   */
  @GetMapping("/representatives")
  public ResponseEntity<List<Representative>> getAllRepresentatives() {
    List<Representative> representatives = representativeService.getAllRepresentatives();
    return ResponseEntity.ok(representatives);
  }

  /**
   * Retrieves a single representative by its ID.
   * @param id The ID of the representative to retrieve.
   * @return The representative if found, or a 404 Not Found response.
   */
  @GetMapping("/representatives/{id}")
  public ResponseEntity<Representative> getRepresentativeById(@PathVariable Integer id) {
    Representative representative = representativeService.getRepresentativeById(id);
    if (representative != null) {
      return ResponseEntity.ok(representative);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Creates a new representative.
   * @param representative The representative object to create.
   * @return The newly created representative with its generated ID.
   */
  @PostMapping("/representatives")
  public ResponseEntity<Representative> createRepresentative(@RequestBody Representative representative) {
    Representative savedRepresentative = representativeService.createRepresentative(representative);
    return new ResponseEntity<>(savedRepresentative, HttpStatus.CREATED);
  }

  /**
   * Updates an existing representative.
   * @param id The ID of the representative to update.
   * @param representative The updated representative object.
   * @return The updated representative if the ID matches, or a 404 Not Found response.
   */
  @PutMapping("/representatives/{id}")
  public ResponseEntity<Representative> updateRepresentative(@PathVariable Integer id, @RequestBody Representative representative) {
    // Assuming the service method handles the existence check
    Representative updatedRepresentative = representativeService.updateRepresentative(id, representative);
    if (updatedRepresentative != null) {
      return ResponseEntity.ok(updatedRepresentative);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Deletes a representative by its ID.
   * @param id The ID of the representative to delete.
   * @return A 200 OK response on successful deletion.
   */
  @DeleteMapping("/representatives/{id}")
  public ResponseEntity<Void> deleteRepresentative(@PathVariable Integer id) {
    representativeService.deleteRepresentative(id);
    return ResponseEntity.ok().build();
  }

}
