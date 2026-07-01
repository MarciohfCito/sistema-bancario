package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WalletRequest {

    @NotNull(message = "Client ID is required")
    private Integer clientId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    private BigDecimal quantity;

    private BigDecimal investedAmount;
}
