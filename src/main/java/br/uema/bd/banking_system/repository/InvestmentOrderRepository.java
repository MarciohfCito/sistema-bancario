package br.uema.bd.banking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.InvestmentOrder;

public interface InvestmentOrderRepository extends JpaRepository<InvestmentOrder, Integer> {
    List<InvestmentOrder> findByAccountId(Integer accountId);
}
