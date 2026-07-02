package br.uema.bd.banking_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uema.bd.banking_system.dto.CardRequest;
import br.uema.bd.banking_system.dto.CardResponse;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.Card;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.CardRepository;
import br.uema.bd.banking_system.util.CardGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;
    private final AccountService accountService;

    public CardResponse create(CardRequest request) {

        Account account = accountService.findAccountById(request.getAccountId());

        String cardNumber = CardGenerator.generateCardNumber();
        String cvv = CardGenerator.generateCVV();
        LocalDate expiration = CardGenerator.generateExpirationDate();

        Card card = new Card();
        card.setAccount(account);
        card.setNumberHash(cardNumber); // aqui você pode depois aplicar hash se quiser
        card.setPrintedName(request.getPrintedName());
        card.setExpirationDate(expiration);
        card.setCvvHash(cvv);
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
        card.setPrintedName(request.getPrintedName());
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
                CardGenerator.maskCard(card.getNumberHash()),
                card.getPrintedName(),
                card.getExpirationDate().toString(),
                card.getCardType(),
                card.getStatus()
        );
    }

    public void createDefaultCard(Account account) {

        String cardNumber = CardGenerator.generateCardNumber();
        String cvv = CardGenerator.generateCVV();
        LocalDate expiration = CardGenerator.generateExpirationDate();

        Card card = new Card();
        card.setAccount(account);
        card.setNumberHash(cardNumber);
        card.setPrintedName(account.getClient().getLegalName());
        card.setExpirationDate(expiration);
        card.setCvvHash(cvv);
        card.setCardType("credito"); // padrão inicial
        card.setStatus("ativo");

        repository.save(card);
    }
}
