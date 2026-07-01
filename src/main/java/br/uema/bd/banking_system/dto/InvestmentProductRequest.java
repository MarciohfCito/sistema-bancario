package br.uema.bd.banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InvestmentProductRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Asset type is required")
    private String assetType;

    private String issuer;

    private String rateIndex;

    private String maturityDate;
}
