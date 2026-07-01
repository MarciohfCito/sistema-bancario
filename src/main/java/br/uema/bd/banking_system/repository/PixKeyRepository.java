package br.uema.bd.banking_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.bd.banking_system.entity.PixKey;

public interface PixKeyRepository extends JpaRepository<PixKey, Integer> {
    Optional<PixKey> findByKeyValue(String keyValue);
    List<PixKey> findByAccountId(Integer accountId);
    boolean existsByKeyValue(String keyValue);
}
