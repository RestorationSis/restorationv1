package com.restorationservice.restorationv1.service;

import com.restorationservice.restorationv1.mapper.AddressMapper;
import com.restorationservice.restorationv1.mapper.ClaimNotesMapper;
import com.restorationservice.restorationv1.model.claim.Claim;
import com.restorationservice.restorationv1.model.claim.ClaimNotes;
import com.restorationservice.restorationv1.repository.ClaimNotesRepository;
import com.restorationservice.restorationv1.repository.ClaimRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimNotesServiceImpl implements ClaimNotesService {

  private final ClaimNotesRepository claimNotesRepository;
  private final ClaimRepository claimRepository;
  private final ClaimNotesMapper claimNotesMapper;

  @Transactional
  @Override
  public com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO addNote(com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO note) {
    Claim claim = claimRepository.findById(note.getClaimId())
        .orElseThrow(() -> new EntityNotFoundException("Claim with ID " + note.getClaimId() + " not found"));

    ClaimNotes claimNotes = claimNotesMapper.toEntity(note, claim);
    claimNotes.setCreatedAt(Instant.now());
    claimNotes.setUpdatedAt(Instant.now());
    return claimNotesMapper.toDTO(claimNotesRepository.save(claimNotes));
  }

  @Transactional
  @Override
  public boolean removeNote(Long noteId) {
    if (claimNotesRepository.existsById(noteId)) {
      claimNotesRepository.deleteById(noteId);
      return true;
    }
    return false;
  }

  @Transactional
  @Override
  public com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO updateNote(Long noteId, com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO updatedNote) {
    ClaimNotes existingNote = claimNotesRepository.findById(noteId)
        .orElseThrow(() -> new IllegalArgumentException("Note with ID " + noteId + " not found"));

    existingNote.setAuthorId(updatedNote.getAuthorId());
    existingNote.setAuthorName(updatedNote.getAuthorName());
    existingNote.setNoteContent(updatedNote.getNoteContent());
    existingNote.setUpdatedAt(Instant.now());

    return claimNotesMapper.toDTO(claimNotesRepository.save(existingNote));
  }

  @Override
  public ClaimNotes getNoteById(Long noteId) {
    return claimNotesRepository.findById(noteId)
        .orElseThrow(() -> new IllegalArgumentException("Note with ID " + noteId + " not found"));
  }

  @Override
  public List<com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO> getNotesByClaimId(Long claimId) {
    return claimNotesRepository.findByClaim_ClaimId(claimId).stream().map(claimNotesMapper::toDTO).collect(
        Collectors.toList());
  }
}
