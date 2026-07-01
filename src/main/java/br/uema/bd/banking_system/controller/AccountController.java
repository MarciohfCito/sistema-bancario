package br.uema.bd.banking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.uema.bd.banking_system.dto.AccountRequest;
import br.uema.bd.banking_system.dto.AccountResponse;
import br.uema.bd.banking_system.dto.BalanceResponse;
import br.uema.bd.banking_system.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Integer id, @Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<Void> block(@PathVariable Integer id) {
        service.block(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Void> close(@PathVariable Integer id) {
        service.close(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> checkBalance(@PathVariable Integer id) {
        return ResponseEntity.ok(service.checkBalance(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AccountResponse>> findByClientId(@PathVariable Integer clientId) {
        return ResponseEntity.ok(service.findByClientId(clientId));
    }
}
