package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.dto.claim.ClaimNotesDTO;
import com.restorationservice.restorationv1.model.claim.Claim;
import com.restorationservice.restorationv1.model.claim.ClaimNotes;
import org.springframework.stereotype.Component;

@Component
public class ClaimNotesMapper {

  public ClaimNotesDTO toDTO(ClaimNotes claimNotes) {
    if (claimNotes == null) {
      return null;
    }

    return ClaimNotesDTO.builder()
        .noteId(claimNotes.getNoteId())
        .claimId(claimNotes.getClaim() != null ? claimNotes.getClaim().getClaimId() : null)
        .authorId(claimNotes.getAuthorId())
        .authorName(claimNotes.getAuthorName())
        .noteContent(claimNotes.getNoteContent())
        .createdAt(claimNotes.getCreatedAt())
        .updatedAt(claimNotes.getUpdatedAt())
        .build();
  }

  public ClaimNotes toEntity(ClaimNotesDTO dto, Claim claim) {
    if (dto == null) {
      return null;
    }

    return ClaimNotes.builder()
        .noteId(dto.getNoteId())
        .claim(claim) // Se pasa explícitamente la entidad Claim desde el servicio
        .authorId(dto.getAuthorId())
        .authorName(dto.getAuthorName())
        .noteContent(dto.getNoteContent())
        .createdAt(dto.getCreatedAt())
        .updatedAt(dto.getUpdatedAt())
        .build();
  }
}
