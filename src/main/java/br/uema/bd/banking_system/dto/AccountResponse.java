package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountResponse {
    private Integer id;
    private Integer clientId;
    private String accountNumber;
    private String branch;
    private String accountType;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private String status;
}
