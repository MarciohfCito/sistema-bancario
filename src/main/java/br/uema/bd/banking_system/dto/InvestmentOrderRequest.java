package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InvestmentOrderRequest {

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotBlank(message = "Order type is required")
    private String orderType;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;

    private BigDecimal totalAmount;
}
