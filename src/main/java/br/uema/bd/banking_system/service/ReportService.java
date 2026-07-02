package br.uema.bd.banking_system.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.Card;
import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.entity.InvestmentOrder;
import br.uema.bd.banking_system.entity.InvestmentProduct;
import br.uema.bd.banking_system.entity.PixKey;
import br.uema.bd.banking_system.entity.Transaction;
import br.uema.bd.banking_system.repository.AccountRepository;
import br.uema.bd.banking_system.repository.CardRepository;
import br.uema.bd.banking_system.repository.ClientRepository;
import br.uema.bd.banking_system.repository.ClientWalletRepository;
import br.uema.bd.banking_system.repository.InvestmentOrderRepository;
import br.uema.bd.banking_system.repository.InvestmentProductRepository;
import br.uema.bd.banking_system.repository.PixKeyRepository;
import br.uema.bd.banking_system.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final PixKeyRepository pixKeyRepository;
    private final TransactionRepository transactionRepository;
    private final InvestmentProductRepository productRepository;
    private final ClientWalletRepository walletRepository;
    private final InvestmentOrderRepository orderRepository;

    public List<Client> reportClients() {
        return clientRepository.findAll();
    }

    public List<Account> reportAccounts() {
        return accountRepository.findAll();
    }

    public List<Card> reportCards() {
        return cardRepository.findAll();
    }

    public List<PixKey> reportPixKeys() {
        return pixKeyRepository.findAll();
    }

    public List<Transaction> reportTransactions() {
        return transactionRepository.findAll();
    }

    public List<InvestmentProduct> reportProducts() {
        return productRepository.findAll();
    }

    public List<InvestmentOrder> reportOrders() {
        return orderRepository.findAll();
    }

    public Map<String, Object> reportTransactionsByPeriod(LocalDateTime start, LocalDateTime end) {
        List<Transaction> transactions = transactionRepository.findByTimestampBetween(start, end);
        long totalTransfers = transactions.stream().filter(t -> "TRANSFER".equals(t.getTransactionType())).count();
        long totalPix = transactions.stream().filter(t -> "PIX".equals(t.getTransactionType())).count();
        long totalPayments = transactions.stream().filter(t -> "PAYMENT".equals(t.getTransactionType())).count();

        Map<String, Object> report = new HashMap<>();
        report.put("periodStart", start);
        report.put("periodEnd", end);
        report.put("totalTransactions", transactions.size());
        report.put("transfers", totalTransfers);
        report.put("pix", totalPix);
        report.put("payments", totalPayments);
        report.put("transactions", transactions);
        return report;
    }

    public Map<String, Object> reportInvestments() {
        List<InvestmentProduct> products = productRepository.findAll();
        List<InvestmentOrder> orders = orderRepository.findAll();

        Map<String, Object> report = new HashMap<>();
        report.put("totalProducts", products.size());
        report.put("activeProducts", products.stream().filter(p -> "active".equals(p.getStatus())).count());
        report.put("totalOrders", orders.size());
        report.put("completedOrders", orders.stream().filter(o -> "completed".equals(o.getStatus())).count());
        report.put("products", products);
        report.put("orders", orders);
        return report;
    }
}
