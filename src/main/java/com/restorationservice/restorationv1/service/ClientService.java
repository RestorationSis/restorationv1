package com.restorationservice.restorationv1.service;

import java.util.List;

import com.restorationservice.restorationv1.model.Customer;


public interface ClientService {
  Customer addClient(Customer client);
  boolean removeClient(String clientId);
  Customer updateClient(Customer client);
  Customer getClientById(String clientId);
  List<Customer> listAllClients();
}
