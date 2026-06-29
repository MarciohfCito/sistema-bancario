package br.uema.bd.sistema_bancario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.sistema_bancario.entity.ProdutosInvestimento;

public interface ProdutosInvestimentoRepository extends JpaRepository<ProdutosInvestimento, Integer> {

}