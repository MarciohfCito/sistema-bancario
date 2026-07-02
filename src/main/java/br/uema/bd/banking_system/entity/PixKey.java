package br.uema.bd.banking_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pix_keys")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "key_type", nullable = false)
    private String keyType;

    @Column(name = "key_value", nullable = false, unique = true)
    private String keyValue;
}
