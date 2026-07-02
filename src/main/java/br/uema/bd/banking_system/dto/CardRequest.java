package br.uema.bd.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardRequest {

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotBlank(message = "Card number is required")
    private String numberHash;

    @NotBlank(message = "Printed name is required")
    private String printedName;

    @NotBlank(message = "Expiration date is required")
    private String expirationDate;

    @NotBlank(message = "CVV is required")
    private String cvvHash;

    @NotBlank(message = "Card type is required")
    private String cardType;
}
