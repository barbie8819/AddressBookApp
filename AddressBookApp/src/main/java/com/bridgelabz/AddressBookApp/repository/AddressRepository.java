package com.bridgelabz.AddressBookApp.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.AddressBookApp.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
