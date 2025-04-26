package com.es.digitalwallet.repository;

import com.es.digitalwallet.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(UUID customerId);
}
