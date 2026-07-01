package br.uema.bd.banking_system.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "investment_products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InvestmentProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "rate_index")
    private String rateIndex;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<ClientWallet> wallets;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<InvestmentOrder> orders;
}
