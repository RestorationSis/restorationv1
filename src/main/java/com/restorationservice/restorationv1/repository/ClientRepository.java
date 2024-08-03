package com.restorationservice.restorationv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
