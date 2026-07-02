package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountRequest {

    @NotNull(message = "Client ID is required")
    private Integer clientId;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Branch is required")
    private String branch;

    @NotBlank(message = "Account type is required")
    private String accountType;

    private BigDecimal overdraftLimit;
}
