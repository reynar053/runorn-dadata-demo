package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
