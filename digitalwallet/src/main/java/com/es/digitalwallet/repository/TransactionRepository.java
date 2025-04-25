package com.es.digitalwallet.repository;

import com.es.digitalwallet.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
