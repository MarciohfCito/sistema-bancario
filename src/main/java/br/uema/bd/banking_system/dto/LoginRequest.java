package br.uema.bd.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Document is required")
    private String document;

    @NotBlank(message = "Password is required")
    private String password;
}
