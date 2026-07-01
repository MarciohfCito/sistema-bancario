package br.uema.bd.banking_system.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "client_type", nullable = false, length = 20)
    private String clientType;

    @Column(name = "document", nullable = false, unique = true, length = 20)
    private String document;

    @Column(name = "legal_name", nullable = false, length = 150)
    private String legalName;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Account> accounts;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<ClientWallet> wallets;
}
