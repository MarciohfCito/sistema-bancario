package br.uema.bd.banking_system.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PixKeyResponse {
    private Integer id;
    private Integer accountId;
    private String keyType;
    private String keyValue;
}
