package br.uema.bd.banking_system.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String token;
    private String clientType;
    private Integer clientId;
    private String name;
}
