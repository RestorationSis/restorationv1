package com.restorationservice.restorationv1.repository;

import java.util.List;

import com.restorationservice.restorationv1.model.customer.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


  @Modifying
  @Query(value = "UPDATE address SET policy_id = :policyId where id = :addressId", nativeQuery = true)
  void addPolicyId(@Param("policyId") long policyId, @Param("addressId") long addressId );
  @Modifying
  @Query(value = "DELETE FROM address WHERE customer_fk = :customerId", nativeQuery = true)
  void deleteAllByCustomerId(@Param("customerId") long customerId);

  @Modifying
  @Query(value = "UPDATE address SET "
      + "street_address = :streetAddress, " +
      "unit_apartment_suite = :unitApartmentSuite, " +
      "city = :city, " +
      "state = :state, " +
      "country = :country, " +
      "zip_code = :zipCode, " +
      "isPrimary = :isPrimary " +
      "WHERE id = :id AND customer_fk = :customerId",
      nativeQuery = true)
  void updateAddress(
      @Param("id") long id,
      @Param("customerId") long customerId,
      @Param("streetAddress") String streetAddress,
      @Param("unitApartmentSuite") String unitApartmentSuite,
      @Param("city") String city,
      @Param("state") String state,
      @Param("country") String country,
      @Param("zipCode") String zipCode,
      @Param("isPrimary") Boolean isPrimary);

  @Query(value = "SELECT * FROM address WHERE customer_fk = :customerId", nativeQuery = true)
  List<Address> findAllByCustomerId(@Param("customerId") Long customerId);

  @Modifying
  @Query(value = "INSERT INTO address (customer_fk, street_address, unit_apartment_suite, city, state, country, zip_code, isPrimary, created_by) " +
      "VALUES (:customerId, :streetAddress, :unitApartmentSuite, :city, :state, :country, :zipCode, :isPrimary, :createdBy)",
      nativeQuery = true)
  void addAddress(
      @Param("customerId") long customerId,
      @Param("streetAddress") String streetAddress,
      @Param("unitApartmentSuite") String unitApartmentSuite,
      @Param("city") String city,
      @Param("state") String state,
      @Param("country") String country,
      @Param("zipCode") String zipCode,
      @Param("isPrimary") Boolean isPrimary,
      @Param("createdBy") String createdBy);
}