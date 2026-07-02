package br.uema.bd.banking_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InvestmentOrderResponse {
    private Integer id;
    private Integer accountId;
    private Integer productId;
    private String orderType;
    private BigDecimal quantity;
    private BigDecimal totalAmount;
    private LocalDateTime requestDate;
    private String status;
}
