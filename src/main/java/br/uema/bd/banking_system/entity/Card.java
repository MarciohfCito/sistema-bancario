package br.uema.bd.banking_system.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cards")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "number_hash")
    private String numberHash;

    @Column(name = "printed_name")
    private String printedName;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "cvv_hash")
    private String cvvHash;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "status")
    private String status;
}
