package com.restorationservice.restorationv1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restorationservice.restorationv1.model.Customer;
import com.restorationservice.restorationv1.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public Customer addClient(Customer client) {
    return clientRepository.save(client);
  }

  @Override
  public boolean removeClient(String clientId) {
    try {
      Long id = Long.parseLong(clientId);
      if (clientRepository.existsById(id)) {
        clientRepository.deleteById(id);
        return true;
      }
    } catch (NumberFormatException e) {
      // Handle the case where clientId is not a valid number
    }
    return false;
  }

  @Override
  public Customer updateClient(Customer client) {
    if (clientRepository.existsById(client.getId())) {
      return clientRepository.save(client);
    }
    throw new IllegalArgumentException("Client with ID " + client.getId() + " does not exist.");
  }

  @Override
  public Customer getClientById(String clientId) {
    try {
      Long id = Long.parseLong(clientId);
      return clientRepository.findById(id).orElse(null);
    } catch (NumberFormatException e) {
      // Handle the case where clientId is not a valid number
    }
    return null;
  }

  @Override
  public List<Customer> listAllClients() {
    return clientRepository.findAll();
  }
}
