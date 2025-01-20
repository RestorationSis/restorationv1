package com.restorationservice.restorationv1.component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SequenceGeneratorService {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public Long getNextSequenceValue() {
    // Bloquear la fila para la transacción
    Long currentId = ((Number) entityManager.createNativeQuery("SELECT id FROM number_sequence FOR UPDATE")
        .getSingleResult()).longValue();

    // Incrementar el valor
    entityManager.createNativeQuery("UPDATE number_sequence SET id = id + 1").executeUpdate();

    // Devolver el nuevo valor
    return currentId + 1;
  }
}


