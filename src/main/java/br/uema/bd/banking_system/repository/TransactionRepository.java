package br.uema.bd.banking_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findBySourceAccountIdOrDestinationAccountIdOrderByTimestampDesc(Integer sourceAccountId, Integer destinationAccountId);
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
