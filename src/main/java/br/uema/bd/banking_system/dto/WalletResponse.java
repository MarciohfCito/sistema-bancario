package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WalletResponse {
    private Integer id;
    private Integer clientId;
    private Integer productId;
    private BigDecimal quantity;
    private BigDecimal investedAmount;
}
