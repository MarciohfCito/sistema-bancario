package br.uema.bd.banking_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByDocument(String document);
    Optional<Client> findByEmail(String email);
    boolean existsByDocument(String document);
    boolean existsByEmail(String email);
}
