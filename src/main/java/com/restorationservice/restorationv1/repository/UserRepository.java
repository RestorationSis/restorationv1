package com.restorationservice.restorationv1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restorationservice.restorationv1.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
  Optional<User> findByUsername(String username);
}
