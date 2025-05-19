package com.restorationservice.restorationv1.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.customer.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
  @Modifying
  @Query(value = "DELETE FROM notes WHERE notes_fk = :customerId", nativeQuery = true)
  void deleteAllByCustomerId(@Param("customerId") long customerId);

  @Modifying
  @Query(value = "UPDATE notes SET " +
      "content = :content, last_update = :lastUpdate " +
      "WHERE id = :id AND notes_fk = :customerId",
      nativeQuery = true)
  void updateNote(
      @Param("id") long id,
      @Param("customerId") long customerId,
      @Param("content") String content,
      @Param("lastUpdate") Date lastUpdate);

  @Modifying
  @Query(value = "INSERT INTO notes (notes_fk, author, content, created_by, created_on, last_update) " +
      "VALUES (:customerId, :author, :content, :createdBy, :createdOn, :lastUpdate)",
      nativeQuery = true)
  void addNote(
      @Param("customerId") long customerId,
      @Param("author") String author,
      @Param("content") String content,
      @Param("createdBy") String createdBy,
      @Param("createdOn") Date createdOn,
      @Param("lastUpdate") Date lastUpdate);

  @Query(value = "SELECT * FROM notes WHERE notes_fk = :customerId", nativeQuery = true)
  List<Note> findAllByCustomerId(@Param("customerId") long customerId);
}

