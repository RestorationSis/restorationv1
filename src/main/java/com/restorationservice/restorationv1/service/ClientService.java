package com.restorationservice.restorationv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restorationservice.restorationv1.model.Client;

import lombok.Setter;


public interface ClientService {
  Client addClient(Client client);
  boolean removeClient(String clientId);
  Client updateClient(Client client);
  Client getClientById(String clientId);
  List<Client> listAllClients();
}
