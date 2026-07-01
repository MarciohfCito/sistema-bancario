package br.uema.bd.banking_system.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.Card;
import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.entity.InvestmentOrder;
import br.uema.bd.banking_system.entity.InvestmentProduct;
import br.uema.bd.banking_system.entity.PixKey;
import br.uema.bd.banking_system.entity.Transaction;
import br.uema.bd.banking_system.service.ReportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> reportClients() {
        return ResponseEntity.ok(service.reportClients());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> reportAccounts() {
        return ResponseEntity.ok(service.reportAccounts());
    }

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> reportCards() {
        return ResponseEntity.ok(service.reportCards());
    }

    @GetMapping("/pix-keys")
    public ResponseEntity<List<PixKey>> reportPixKeys() {
        return ResponseEntity.ok(service.reportPixKeys());
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> reportTransactions() {
        return ResponseEntity.ok(service.reportTransactions());
    }

    @GetMapping("/products")
    public ResponseEntity<List<InvestmentProduct>> reportProducts() {
        return ResponseEntity.ok(service.reportProducts());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<InvestmentOrder>> reportOrders() {
        return ResponseEntity.ok(service.reportOrders());
    }

    @GetMapping("/transactions-by-period")
    public ResponseEntity<Map<String, Object>> reportTransactionsByPeriod(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(service.reportTransactionsByPeriod(start, end));
    }

    @GetMapping("/investments")
    public ResponseEntity<Map<String, Object>> reportInvestments() {
        return ResponseEntity.ok(service.reportInvestments());
    }
}
