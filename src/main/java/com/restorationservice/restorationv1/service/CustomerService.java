package com.restorationservice.restorationv1.service;

import java.util.List;

import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.customer.CustomerStatus;
import com.restorationservice.restorationv1.model.customer.Note;
import com.restorationservice.restorationv1.model.dto.AddressDTO;
import com.restorationservice.restorationv1.model.dto.AddressDTOnoPolicy;
import com.restorationservice.restorationv1.model.dto.ContactDTO;
import com.restorationservice.restorationv1.model.dto.CustomerDTO;
import com.restorationservice.restorationv1.model.dto.NoteDTO;
import com.restorationservice.restorationv1.model.policy.Contact;


public interface CustomerService {
  Customer addClient(Customer client);
  boolean removeClient(String clientId);
  Customer updateClient(Customer client);
  CustomerDTO updateClientPersonalInfo(CustomerDTO client);
  void updateCustomerStatus(Long id, CustomerStatus status);
  AddressDTOnoPolicy getAddressById(String addressId);
  AddressDTO updateClientAddress(AddressDTO address);
  AddressDTO addAddress(AddressDTO address);
  boolean removeAddress(String addressId);
  NoteDTO updateClientNote(NoteDTO note);
  NoteDTO addNote(NoteDTO note);
  List<Note> getNotesByCustomerId(String customerId);
  boolean removeNote(long customerId, String noteId);
  Customer getClientById(String clientId);
  List<Customer> getCustomersFiltered(String status, String createdBy);
  List<Customer> listAllClients();
  ContactDTO addContact(ContactDTO contact);
  ContactDTO updateContact(ContactDTO contact);
  boolean removeContact(String contactId);
  ContactDTO getContactById(String contactId);
  List<ContactDTO> getContactsByCustomerId(String customerId);
}
