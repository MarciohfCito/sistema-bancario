package br.uema.bd.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardRequest {

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotBlank(message = "Printed name is required")
    private String printedName;

    @NotBlank(message = "Card type is required")
    private String cardType;
}
