package br.uema.bd.banking_system.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "branch")
    private String branch;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "overdraft_limit")
    private BigDecimal overdraftLimit;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Card> cards;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<PixKey> pixKeys;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<InvestmentOrder> investmentOrders;

    @OneToMany(mappedBy = "sourceAccount")
    @ToString.Exclude
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "destinationAccount")
    @ToString.Exclude
    private List<Transaction> incomingTransactions;
}
