package br.uema.bd.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.InvestmentProduct;

public interface InvestmentProductRepository extends JpaRepository<InvestmentProduct, Integer> {
}
