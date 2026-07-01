package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransactionResponse {
    private Integer id;
    private Integer sourceAccountId;
    private Integer destinationAccountId;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime timestamp;
    private String status;
}
