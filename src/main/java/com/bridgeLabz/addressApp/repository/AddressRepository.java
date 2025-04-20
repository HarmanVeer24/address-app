package com.bridgeLabz.addressApp.repository;

import com.bridgeLabz.addressApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{
}
