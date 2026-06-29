package br.uema.bd.sistema_bancario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.sistema_bancario.entity.ChavesPix;

public interface ChavesPixRepository extends JpaRepository<ChavesPix, Integer> {

}