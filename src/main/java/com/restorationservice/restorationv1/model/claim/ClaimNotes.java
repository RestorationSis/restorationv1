package com.restorationservice.restorationv1.model.claim;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(EntityChangeLogListener.class)
@Table(name = "claim_notes")
public class ClaimNotes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "note_id", updatable = false, nullable = false)
  private Long noteId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "claim_id", referencedColumnName = "claim_id", nullable = false)
  private Claim claim;

  @NotNull
  @Column(name = "author_id", nullable = false)
  private Long authorId;

  @Column(name = "author_name")
  private String authorName;

  @NotNull
  @Column(name = "note_content", columnDefinition = "TEXT", nullable = false)
  private String noteContent;

  @NotNull
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;
}
