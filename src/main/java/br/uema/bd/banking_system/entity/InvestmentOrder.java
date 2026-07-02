package br.uema.bd.banking_system.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "investment_orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InvestmentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private InvestmentProduct product;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "status")
    private String status;
}
