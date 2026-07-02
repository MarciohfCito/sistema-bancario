package br.uema.bd.banking_system.dto;

import java.time.LocalDate;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InvestmentProductResponse {
    private Integer id;
    private String productName;
    private String assetType;
    private String issuer;
    private String rateIndex;
    private LocalDate maturityDate;
    private String status;
}
