package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Source account is required")
    private Integer sourceAccountId;

    private Integer destinationAccountId;

    private String pixKeyDestination;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    private String transactionType;
}
