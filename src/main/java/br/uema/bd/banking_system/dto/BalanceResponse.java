package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BalanceResponse {
    private Integer accountId;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private BigDecimal availableBalance;
    private LocalDateTime queryDate;
}
