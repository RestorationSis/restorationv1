package com.restorationservice.restorationv1.service;

import com.restorationservice.restorationv1.model.claim.ClaimNotes;

import java.util.List;

public interface ClaimNotesService {

  com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO addNote(com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO note);

  boolean removeNote(Long noteId);

  com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO updateNote(Long noteId, com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO note);

  ClaimNotes getNoteById(Long noteId);

  List<com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO> getNotesByClaimId(Long claimId);
}
