package br.uema.bd.banking_system.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uema.bd.banking_system.dto.AccountRequest;
import br.uema.bd.banking_system.dto.AccountResponse;
import br.uema.bd.banking_system.dto.BalanceResponse;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.exception.BusinessException;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.AccountRepository;
import br.uema.bd.banking_system.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final ClientRepository clientRepository;

    private Client findClientById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client not found: " + id));
    }

    public AccountResponse create(AccountRequest request) {
        if (repository.findByAccountNumber(request.getAccountNumber()).isPresent()) {
            throw new BusinessException("Account number already exists");
        }

        Client client = findClientById(request.getClientId());        Account account = new Account();
        account.setClient(client);
        account.setAccountNumber(request.getAccountNumber());
        account.setBranch(request.getBranch());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setOverdraftLimit(request.getOverdraftLimit() != null ? request.getOverdraftLimit() : BigDecimal.ZERO);
        account.setStatus("active");

        return toResponse(repository.save(account));
    }

    public List<AccountResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AccountResponse findById(Integer id) {
        return toResponse(findAccountById(id));
    }

    public AccountResponse update(Integer id, AccountRequest request) {
        Account account = findAccountById(id);
        if (!account.getAccountNumber().equals(request.getAccountNumber())
                && repository.findByAccountNumber(request.getAccountNumber()).isPresent()) {
            throw new BusinessException("Account number already exists");
        }

        Client client = findClientById(request.getClientId());
        account.setClient(client);
        account.setAccountNumber(request.getAccountNumber());
        account.setBranch(request.getBranch());
        account.setAccountType(request.getAccountType());
        account.setOverdraftLimit(request.getOverdraftLimit() != null ? request.getOverdraftLimit() : BigDecimal.ZERO);

        return toResponse(repository.save(account));
    }

    @Transactional
    public void block(Integer id) {
        Account account = findAccountById(id);
        if ("blocked".equals(account.getStatus()) || "closed".equals(account.getStatus())) {
            throw new BusinessException("Account already " + account.getStatus());
        }
        account.setStatus("blocked");
        repository.save(account);
    }

    @Transactional
    public void close(Integer id) {
        Account account = findAccountById(id);
        if ("closed".equals(account.getStatus())) {
            throw new BusinessException("Account already closed");
        }
        account.setStatus("closed");
        repository.save(account);
    }

    public BalanceResponse checkBalance(Integer id) {
        Account account = findAccountById(id);
        return new BalanceResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getOverdraftLimit(),
                account.getBalance().add(account.getOverdraftLimit()),
                LocalDateTime.now()
        );
    }

    public List<AccountResponse> findByClientId(Integer clientId) {
        return repository.findByClientId(clientId).stream().map(this::toResponse).toList();
    }

    public Account findAccountById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getClient().getId(),
                account.getAccountNumber(),
                account.getBranch(),
                account.getAccountType(),
                account.getBalance(),
                account.getOverdraftLimit(),
                account.getStatus()
        );
    }

    //Gera o numero da conta automaticamente ao cadastrar o cliente
    private String generateAccountNumber() {

        return repository.findTopByOrderByAccountNumberDesc()
                .map(account -> {

                    int number = Integer.parseInt(account.getAccountNumber());

                    return String.format("%06d", number + 1);

                })
                .orElse("000001");
    }

    //Gera a agencia automaticamente ao cadastrar o cliente
    private String generateBranch() {
        return "0001";
    }

    //Cria uma conta padrão para o cliente ao ser cadastrado
    @Transactional
    public Account createDefaultAccount(Client client) {

        Account account = new Account();

        account.setClient(client);

        account.setAccountNumber(generateAccountNumber());

        account.setBranch(generateBranch());

        account.setAccountType("conta-corrente");

        account.setBalance(BigDecimal.ZERO);

        account.setOverdraftLimit(BigDecimal.ZERO);

        account.setStatus("ativa");

        return repository.save(account);
    }
}
