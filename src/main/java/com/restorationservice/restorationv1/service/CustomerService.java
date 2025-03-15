package com.restorationservice.restorationv1.service;

import java.util.List;
import java.util.Optional;

import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.dto.AddressDTO;
import com.restorationservice.restorationv1.model.dto.NoteDTO;


public interface CustomerService {
  Customer addClient(Customer client);
  boolean removeClient(String clientId);
  Customer updateClient(Customer client);
  Address getAddressById(String addressId);
  AddressDTO updateClientAddress(AddressDTO address);
  AddressDTO addAddress(AddressDTO address);
  boolean removeAddress(String addressId);
  NoteDTO updateClientNote(NoteDTO note);
  NoteDTO addNote(NoteDTO note);
  boolean removeNote(long customerId, String noteId);
  Customer getClientById(String clientId);
  List<Customer> listAllClients();
}
