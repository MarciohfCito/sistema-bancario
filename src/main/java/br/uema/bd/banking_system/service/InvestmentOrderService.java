package br.uema.bd.banking_system.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uema.bd.banking_system.dto.InvestmentOrderRequest;
import br.uema.bd.banking_system.dto.InvestmentOrderResponse;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.InvestmentOrder;
import br.uema.bd.banking_system.entity.InvestmentProduct;
import br.uema.bd.banking_system.exception.BusinessException;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.InvestmentOrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentOrderService {

    private final InvestmentOrderRepository repository;
    private final AccountService accountService;
    private final InvestmentProductService productService;

    @Transactional
    public InvestmentOrderResponse create(InvestmentOrderRequest request) {
        Account account = accountService.findAccountById(request.getAccountId());
        InvestmentProduct product = productService.findProductById(request.getProductId());

        BigDecimal totalAmount = request.getTotalAmount() != null
                ? request.getTotalAmount()
                : BigDecimal.ZERO;

        if ("BUY".equalsIgnoreCase(request.getOrderType())) {
            BigDecimal availableBalance = account.getBalance().add(account.getOverdraftLimit());
            if (availableBalance.compareTo(totalAmount) < 0) {
                throw new BusinessException("Insufficient balance to place buy order");
            }
            account.setBalance(account.getBalance().subtract(totalAmount));
        }

        InvestmentOrder order = new InvestmentOrder();
        order.setAccount(account);
        order.setProduct(product);
        order.setOrderType(request.getOrderType());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(totalAmount);
        order.setRequestDate(LocalDateTime.now());
        order.setStatus("completed");

        return toResponse(repository.save(order));
    }

    public List<InvestmentOrderResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public InvestmentOrderResponse findById(Integer id) {
        return toResponse(findOrderById(id));
    }

    public List<InvestmentOrderResponse> findByAccountId(Integer accountId) {
        return repository.findByAccountId(accountId).stream().map(this::toResponse).toList();
    }

    private InvestmentOrder findOrderById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment order not found: " + id));
    }

    private InvestmentOrderResponse toResponse(InvestmentOrder o) {
        return new InvestmentOrderResponse(
                o.getId(), o.getAccount().getId(),
                o.getProduct().getId(), o.getOrderType(),
                o.getQuantity(), o.getTotalAmount(),
                o.getRequestDate(), o.getStatus()
        );
    }
}
