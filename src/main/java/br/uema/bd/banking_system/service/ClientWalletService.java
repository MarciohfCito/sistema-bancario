package br.uema.bd.banking_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.dto.WalletRequest;
import br.uema.bd.banking_system.dto.WalletResponse;
import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.entity.ClientWallet;
import br.uema.bd.banking_system.entity.InvestmentProduct;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.ClientWalletRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientWalletService {

    private final ClientWalletRepository repository;
    private final ClientService clientService;
    private final InvestmentProductService productService;

    public WalletResponse create(WalletRequest request) {
        Client client = clientService.findClientById(request.getClientId());
        InvestmentProduct product = productService.findProductById(request.getProductId());
        ClientWallet wallet = new ClientWallet();
        wallet.setClient(client);
        wallet.setProduct(product);
        wallet.setQuantity(request.getQuantity());
        wallet.setInvestedAmount(request.getInvestedAmount());
        return toResponse(repository.save(wallet));
    }

    public List<WalletResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public WalletResponse findById(Integer id) {
        return toResponse(findWalletById(id));
    }

    public WalletResponse update(Integer id, WalletRequest request) {
        ClientWallet wallet = findWalletById(id);
        wallet.setClient(clientService.findClientById(request.getClientId()));
        wallet.setProduct(productService.findProductById(request.getProductId()));
        wallet.setQuantity(request.getQuantity());
        wallet.setInvestedAmount(request.getInvestedAmount());
        return toResponse(repository.save(wallet));
    }

    public void delete(Integer id) {
        repository.delete(findWalletById(id));
    }

    public List<WalletResponse> findByClientId(Integer clientId) {
        return repository.findByClientId(clientId).stream().map(this::toResponse).toList();
    }

    private ClientWallet findWalletById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found: " + id));
    }

    private WalletResponse toResponse(ClientWallet w) {
        return new WalletResponse(
                w.getId(),
                w.getClient().getId(),
                w.getProduct().getId(),
                w.getQuantity(),
                w.getInvestedAmount()
        );
    }
}
