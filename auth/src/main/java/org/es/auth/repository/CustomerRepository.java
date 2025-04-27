package org.es.auth.repository;



import org.es.auth.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    Customer findById(UUID id);
    Customer findByEmail(String email);
    Customer findByPhone(String phone);
    @Modifying
    @Transactional
    @Query("UPDATE Customer u SET u.password = :password WHERE u.username = :username")
    void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}

