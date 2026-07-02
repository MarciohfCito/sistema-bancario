package br.uema.bd.banking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.uema.bd.banking_system.dto.InvestmentOrderRequest;
import br.uema.bd.banking_system.dto.InvestmentOrderResponse;
import br.uema.bd.banking_system.service.InvestmentOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class InvestmentOrderController {

    private final InvestmentOrderService service;

    @PostMapping
    public ResponseEntity<InvestmentOrderResponse> create(@Valid @RequestBody InvestmentOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<InvestmentOrderResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentOrderResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<InvestmentOrderResponse>> findByAccountId(@PathVariable Integer accountId) {
        return ResponseEntity.ok(service.findByAccountId(accountId));
    }
}
