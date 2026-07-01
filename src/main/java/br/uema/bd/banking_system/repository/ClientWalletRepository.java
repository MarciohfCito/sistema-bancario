package br.uema.bd.banking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.ClientWallet;

public interface ClientWalletRepository extends JpaRepository<ClientWallet, Integer> {
    List<ClientWallet> findByClientId(Integer clientId);
}
