package br.uema.bd.banking_system.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientResponse {
    private Integer id;
    private String clientType;
    private String document;
    private String legalName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}
