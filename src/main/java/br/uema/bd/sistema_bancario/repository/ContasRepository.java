package br.uema.bd.sistema_bancario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.sistema_bancario.entity.Contas;

public interface ContasRepository extends JpaRepository<Contas, Integer> {

}