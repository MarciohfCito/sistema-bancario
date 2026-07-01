package br.uema.bd.banking_system.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uema.bd.banking_system.dto.TransactionRequest;
import br.uema.bd.banking_system.dto.TransactionResponse;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.PixKey;
import br.uema.bd.banking_system.entity.Transaction;
import br.uema.bd.banking_system.exception.BusinessException;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;
    private final PixKeyService pixKeyService;

    @Transactional
    public TransactionResponse transfer(TransactionRequest request) {
        Account source = validateActiveAccount(request.getSourceAccountId());
        Account destination = validateActiveAccount(request.getDestinationAccountId());

        debit(source, request.getAmount());
        credit(destination, request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setDestinationAccount(destination);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("completed");

        return toResponse(repository.save(transaction));
    }

    @Transactional
    public TransactionResponse pay(TransactionRequest request) {
        Account source = validateActiveAccount(request.getSourceAccountId());

        debit(source, request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("PAYMENT");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("completed");

        return toResponse(repository.save(transaction));
    }

    @Transactional
    public TransactionResponse pix(TransactionRequest request) {
        Account source = validateActiveAccount(request.getSourceAccountId());
        PixKey destinationKey = pixKeyService.findByKeyValue(request.getPixKeyDestination());
        Account destination = destinationKey.getAccount();

        if (!"active".equals(destination.getStatus())) {
            throw new BusinessException("Destination account is not active");
        }

        debit(source, request.getAmount());
        credit(destination, request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setDestinationAccount(destination);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("PIX");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("completed");

        return toResponse(repository.save(transaction));
    }

    public List<TransactionResponse> findByAccountId(Integer accountId) {
        return repository.findBySourceAccountIdOrDestinationAccountIdOrderByTimestampDesc(accountId, accountId)
                .stream().map(this::toResponse).toList();
    }

    public TransactionResponse findById(Integer id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id)));
    }

    public List<TransactionResponse> findByPeriod(LocalDateTime start, LocalDateTime end) {
        return repository.findByTimestampBetween(start, end).stream().map(this::toResponse).toList();
    }

    private Account validateActiveAccount(Integer id) {
        Account account = accountService.findAccountById(id);
        if (!"active".equals(account.getStatus())) {
            throw new BusinessException("Account is not active");
        }
        return account;
    }

    private void debit(Account account, BigDecimal amount) {
        BigDecimal availableBalance = account.getBalance().add(account.getOverdraftLimit());
        if (availableBalance.compareTo(amount) < 0) {
            throw new BusinessException("Insufficient balance in account " + account.getAccountNumber());
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    private void credit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
    }

    private TransactionResponse toResponse(Transaction t) {
        Integer sourceId = t.getSourceAccount() != null ? t.getSourceAccount().getId() : null;
        Integer destId = t.getDestinationAccount() != null ? t.getDestinationAccount().getId() : null;
        return new TransactionResponse(
                t.getId(), sourceId, destId,
                t.getAmount(), t.getTransactionType(),
                t.getTimestamp(), t.getStatus()
        );
    }
}
