package com.restorationservice.restorationv1.component;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorationservice.restorationv1.model.ChangeLog;
import com.restorationservice.restorationv1.repository.ChangeLogRepository;
import com.restorationservice.restorationv1.security.SecurityUtils;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

@Component
public class EntityChangeLogListener {
  private static ChangeLogRepository changelogRepository;

  @Autowired
  public void setChangelogRepository(ChangeLogRepository repository) {
    changelogRepository = repository;
  }

  @PrePersist
  public void onPrePersist(Object entity) {
    String entityName = entity.getClass().getSimpleName();
    String username = SecurityUtils.getAuthenticatedUsername();
    String changes = serializeEntity(entity);

    saveChange(entityName, username, "CREATE", changes);
  }

  @PreUpdate
  public void onPreUpdate(Object entity) {
    String entityName = entity.getClass().getSimpleName();
    String username = SecurityUtils.getAuthenticatedUsername();
    String changes = serializeEntity(entity); // Puedes mejorar esto comparando valores antiguos vs nuevos

    saveChange(entityName, username, "UPDATE", changes);
  }

  @PreRemove
  public void onPreRemove(Object entity) {
    String entityName = entity.getClass().getSimpleName();
    String changes = serializeEntity(entity);
    String username = SecurityUtils.getAuthenticatedUsername();

    saveChange(entityName, username, "DELETE", changes);
  }

  private static void saveChange(String entityName, String user, String action, String changes) {
    ChangeLog log = new ChangeLog(entityName, user, action, changes);
    changelogRepository.save(log);
  }

  private String serializeEntity(Object entity) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(entity);
    } catch (JsonProcessingException e) {
      return "{}";
    }
  }
}
