package com.restorationservice.restorationv1.dto.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimNotesDTO {

  private Long noteId;
  private Long claimId;
  private Long authorId;
  private String authorName;
  private String noteContent;
  private Instant createdAt;
  private Instant updatedAt;
}
