package br.uema.bd.banking_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.dto.PixKeyRequest;
import br.uema.bd.banking_system.dto.PixKeyResponse;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.PixKey;
import br.uema.bd.banking_system.exception.BusinessException;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.PixKeyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PixKeyService {

    private final PixKeyRepository repository;
    private final AccountService accountService;

    public PixKeyResponse create(PixKeyRequest request) {
        if (repository.existsByKeyValue(request.getKeyValue())) {
            throw new BusinessException("PIX key already registered");
        }
        Account account = accountService.findAccountById(request.getAccountId());
        PixKey pixKey = new PixKey();
        pixKey.setAccount(account);
        pixKey.setKeyType(request.getKeyType());
        pixKey.setKeyValue(request.getKeyValue());
        return toResponse(repository.save(pixKey));
    }

    public List<PixKeyResponse> findByAccountId(Integer accountId) {
        return repository.findByAccountId(accountId).stream().map(this::toResponse).toList();
    }

    public PixKeyResponse findById(Integer id) {
        return toResponse(findPixKeyById(id));
    }

    public PixKey findByKeyValue(String keyValue) {
        return repository.findByKeyValue(keyValue)
                .orElseThrow(() -> new ResourceNotFoundException("PIX key not found: " + keyValue));
    }

    public void delete(Integer id) {
        repository.delete(findPixKeyById(id));
    }

    private PixKey findPixKeyById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PIX key not found: " + id));
    }

    private PixKeyResponse toResponse(PixKey pixKey) {
        return new PixKeyResponse(
                pixKey.getId(),
                pixKey.getAccount().getId(),
                pixKey.getKeyType(),
                pixKey.getKeyValue()
        );
    }
}
