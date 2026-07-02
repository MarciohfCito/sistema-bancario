package br.uema.bd.banking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByAccountId(Integer accountId);
}
