package br.uema.bd.banking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientRequest {

    @NotBlank(message = "Client type is required")
    private String clientType;

    @NotBlank(message = "Document is required")
    @Size(max = 20)
    private String document;

    @NotBlank(message = "Legal name is required")
    @Size(max = 150)
    private String legalName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Size(max = 120)
    private String email;

    @Size(max = 20)
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;
}
