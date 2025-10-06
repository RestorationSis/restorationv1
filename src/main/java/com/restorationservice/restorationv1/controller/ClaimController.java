package com.restorationservice.restorationv1.controller;

import com.restorationservice.restorationv1.model.claim.ClaimInvoice;
import com.restorationservice.restorationv1.model.claim.ClaimNotes;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInsuranceAdjusterDTO;
import com.restorationservice.restorationv1.model.dto.claim.ClaimInvoiceDTO;
import com.restorationservice.restorationv1.service.ClaimInsuranceAdjusterService;
import com.restorationservice.restorationv1.service.ClaimInvoiceService;
import com.restorationservice.restorationv1.service.ClaimNotesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/claim")
@RequiredArgsConstructor
public class ClaimController {

  private final ClaimNotesService claimNotesService;
  private final ClaimInvoiceService claimInvoiceService;
  private final ClaimInsuranceAdjusterService claimInsuranceAdjusterService;

  @PostMapping("/notes")
  public ResponseEntity<com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO> addNote(
      @Valid @RequestBody com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO request) {

    com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO note = claimNotesService.addNote(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(note);
  }


  @PutMapping("/notes/{claimNoteId}")
  public ResponseEntity<com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO> updateNote(
      @PathVariable Long claimNoteId,
      @Valid @RequestBody com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO request) {
    try {
      com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO updatedNote = claimNotesService.updateNote(
          claimNoteId, request);
      return ResponseEntity.ok(updatedNote);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/notes/{noteId}")
  public ResponseEntity<Void> removeNote(@PathVariable Long noteId) {
    boolean isRemoved = claimNotesService.removeNote(noteId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{claimId}/notes")
  public ResponseEntity<List<com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO>> getNotesByClaimId(
      @PathVariable Long claimId) {
    List<com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO> notes = claimNotesService.getNotesByClaimId(
        claimId);
    if (notes != null && !notes.isEmpty()) {
      return ResponseEntity.ok(notes);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @GetMapping("/notes/{noteId}")
  public ResponseEntity<ClaimNotes> getNoteById(@PathVariable Long noteId) {
    ClaimNotes note = claimNotesService.getNoteById(noteId);
    if (note != null) {
      return ResponseEntity.ok(note);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/invoices")
  public ResponseEntity<ClaimInvoiceDTO> addInvoice(@Valid @RequestBody ClaimInvoiceDTO request) {
    ClaimInvoiceDTO invoice = claimInvoiceService.addInvoice(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
  }

  @PutMapping("/invoices/{invoiceId}")
  public ResponseEntity<ClaimInvoiceDTO> updateInvoice(
      @PathVariable Long invoiceId,
      @Valid @RequestBody ClaimInvoiceDTO request) {
    try {
      ClaimInvoiceDTO updatedInvoice = claimInvoiceService.updateInvoice(invoiceId, request);
      return ResponseEntity.ok(updatedInvoice);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/invoices/{invoiceId}")
  public ResponseEntity<Void> removeInvoice(@PathVariable Long invoiceId) {
    boolean isRemoved = claimInvoiceService.removeInvoice(invoiceId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{claimId}/invoices")
  public ResponseEntity<List<ClaimInvoiceDTO>> getInvoicesByClaimId(@PathVariable Long claimId) {
    List<ClaimInvoiceDTO> invoices = claimInvoiceService.getInvoicesByClaimId(claimId);
    if (invoices != null && !invoices.isEmpty()) {
      return ResponseEntity.ok(invoices);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @GetMapping("/invoices/{invoiceId}")
  public ResponseEntity<ClaimInvoiceDTO> getInvoiceById(@PathVariable Long invoiceId) {
    ClaimInvoiceDTO invoice = claimInvoiceService.getInvoiceById(invoiceId);
    if (invoice != null) {
      return ResponseEntity.ok(invoice);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/adjusters")
  public ResponseEntity<ClaimInsuranceAdjusterDTO> addAdjuster(@Valid @RequestBody ClaimInsuranceAdjusterDTO request) {
    ClaimInsuranceAdjusterDTO adjuster = claimInsuranceAdjusterService.addAdjuster(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(adjuster);
  }

  @PutMapping("/adjusters/{adjusterId}")
  public ResponseEntity<ClaimInsuranceAdjusterDTO> updateAdjuster(@PathVariable Long adjusterId,
      @Valid @RequestBody ClaimInsuranceAdjusterDTO request) {
    try {
      ClaimInsuranceAdjusterDTO updated = claimInsuranceAdjusterService.updateAdjuster(adjusterId, request);
      return ResponseEntity.ok(updated);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/adjusters/{adjusterId}")
  public ResponseEntity<Void> removeAdjuster(@PathVariable Long adjusterId) {
    boolean removed = claimInsuranceAdjusterService.removeAdjuster(adjusterId);
    return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @GetMapping("/{claimId}/adjusters")
  public ResponseEntity<List<ClaimInsuranceAdjusterDTO>> getAdjustersByClaimId(@PathVariable Long claimId) {
    List<ClaimInsuranceAdjusterDTO> adjusters = claimInsuranceAdjusterService.getAdjustersByClaimId(claimId);
    return adjusters.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(adjusters);
  }

  @GetMapping("/adjusters/{adjusterId}")
  public ResponseEntity<ClaimInsuranceAdjusterDTO> getAdjusterById(@PathVariable Long adjusterId) {
    try {
      ClaimInsuranceAdjusterDTO adjuster = claimInsuranceAdjusterService.getAdjusterById(adjusterId);
      return ResponseEntity.ok(adjuster);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/adjusters/all")
  public ResponseEntity<List<ClaimInsuranceAdjusterDTO>> getAllAdjuster() {
    try {
      List<ClaimInsuranceAdjusterDTO>adjuster = claimInsuranceAdjusterService.getAllAdjuster();
      return ResponseEntity.ok(adjuster);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
