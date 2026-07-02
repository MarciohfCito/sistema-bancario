package br.uema.bd.banking_system.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "status")
    private String status;
}
