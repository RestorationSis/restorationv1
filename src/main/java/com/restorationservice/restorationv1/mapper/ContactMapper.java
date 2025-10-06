package com.restorationservice.restorationv1.mapper;

import org.springframework.stereotype.Component;

import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.dto.ContactDTO;
import com.restorationservice.restorationv1.model.policy.Contact;
@Component
public class ContactMapper {

  public  ContactDTO toDto(Contact contact) {
    if (contact == null) {
      return null;
    }

    return new ContactDTO(
   contact.getId(),
        contact.getName(),
        contact.getEmail(),
        contact.getPhone(),
        contact.getContactTpe(),
        contact.getRelation(),
        contact.getCustomer() != null ? contact.getCustomer().getId() : null
    );
  }

  // Convierte de DTO a entidad
  public  Contact toEntity(ContactDTO dto, Customer customer) {
    if (dto == null) {
      return null;
    }

    Contact contact = new Contact();
    contact.setId(dto.getId()); // opcional si estás creando nuevo
    contact.setName(dto.getName());
    contact.setEmail(dto.getEmail());
    contact.setPhone(dto.getPhone());
    contact.setContactTpe(dto.getContactTpe());
    contact.setRelation(dto.getRelation());
    contact.setCustomer(customer); // ya debe estar cargado

    return contact;
  }
}
