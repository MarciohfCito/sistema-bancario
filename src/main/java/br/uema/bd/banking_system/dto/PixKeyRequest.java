package br.uema.bd.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PixKeyRequest {

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotBlank(message = "Key type is required")
    private String keyType;

    @NotBlank(message = "Key value is required")
    private String keyValue;
}
