package br.uema.bd.banking_system.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardResponse {
    private Integer id;
    private Integer accountId;
    private String number;
    private String printedName;
    private String expirationDate;
    private String cardType;
    private String status;
}
