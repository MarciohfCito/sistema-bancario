package br.uema.bd.banking_system.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client_wallets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private InvestmentProduct product;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "invested_amount")
    private BigDecimal investedAmount;
}
