package br.uema.bd.banking_system.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.dto.InvestmentProductRequest;
import br.uema.bd.banking_system.dto.InvestmentProductResponse;
import br.uema.bd.banking_system.entity.InvestmentProduct;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.InvestmentProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentProductService {

    private final InvestmentProductRepository repository;

    public InvestmentProductResponse create(InvestmentProductRequest request) {
        InvestmentProduct product = new InvestmentProduct();
        product.setProductName(request.getProductName());
        product.setAssetType(request.getAssetType());
        product.setIssuer(request.getIssuer());
        product.setRateIndex(request.getRateIndex());
        product.setMaturityDate(request.getMaturityDate() != null ? LocalDate.parse(request.getMaturityDate()) : null);
        product.setStatus("active");
        return toResponse(repository.save(product));
    }

    public List<InvestmentProductResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public InvestmentProductResponse findById(Integer id) {
        return toResponse(findProductById(id));
    }

    public InvestmentProductResponse update(Integer id, InvestmentProductRequest request) {
        InvestmentProduct product = findProductById(id);
        product.setProductName(request.getProductName());
        product.setAssetType(request.getAssetType());
        product.setIssuer(request.getIssuer());
        product.setRateIndex(request.getRateIndex());
        product.setMaturityDate(request.getMaturityDate() != null ? LocalDate.parse(request.getMaturityDate()) : null);
        return toResponse(repository.save(product));
    }

    public void delete(Integer id) {
        repository.delete(findProductById(id));
    }

    public InvestmentProduct findProductById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment product not found: " + id));
    }

    private InvestmentProductResponse toResponse(InvestmentProduct p) {
        return new InvestmentProductResponse(
                p.getId(), p.getProductName(), p.getAssetType(),
                p.getIssuer(), p.getRateIndex(), p.getMaturityDate(), p.getStatus()
        );
    }
}
