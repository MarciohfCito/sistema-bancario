package br.uema.bd.banking_system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uema.bd.banking_system.dto.CardRequest;
import br.uema.bd.banking_system.dto.CardResponse;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.Card;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.CardRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;
    private final AccountService accountService;

    public CardResponse create(CardRequest request) {
        Account account = accountService.findAccountById(request.getAccountId());
        Card card = new Card();
        card.setAccount(account);
        card.setNumberHash(request.getNumberHash());
        card.setPrintedName(request.getPrintedName());
        card.setExpirationDate(java.time.LocalDate.parse(request.getExpirationDate()));
        card.setCvvHash(request.getCvvHash());
        card.setCardType(request.getCardType());
        card.setStatus("active");
        return toResponse(repository.save(card));
    }

    public List<CardResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CardResponse findById(Integer id) {
        return toResponse(findCardById(id));
    }

    public CardResponse update(Integer id, CardRequest request) {
        Card card = findCardById(id);
        Account account = accountService.findAccountById(request.getAccountId());
        card.setAccount(account);
        card.setNumberHash(request.getNumberHash());
        card.setPrintedName(request.getPrintedName());
        card.setExpirationDate(java.time.LocalDate.parse(request.getExpirationDate()));
        card.setCvvHash(request.getCvvHash());
        card.setCardType(request.getCardType());
        return toResponse(repository.save(card));
    }

    @Transactional
    public void block(Integer id) {
        Card card = findCardById(id);
        card.setStatus("blocked");
        repository.save(card);
    }

    public void delete(Integer id) {
        repository.delete(findCardById(id));
    }

    public List<CardResponse> findByAccountId(Integer accountId) {
        return repository.findByAccountId(accountId).stream().map(this::toResponse).toList();
    }

    private Card findCardById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found: " + id));
    }

    private CardResponse toResponse(Card card) {
        return new CardResponse(
                card.getId(),
                card.getAccount().getId(),
                card.getPrintedName(),
                card.getExpirationDate().toString(),
                card.getCardType(),
                card.getStatus()
        );
    }
}
