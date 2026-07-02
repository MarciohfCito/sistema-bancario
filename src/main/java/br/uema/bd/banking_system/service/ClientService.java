package br.uema.bd.banking_system.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.dto.ClientRequest;
import br.uema.bd.banking_system.dto.ClientResponse;
import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.exception.BusinessException;
import br.uema.bd.banking_system.exception.ResourceNotFoundException;
import br.uema.bd.banking_system.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Transactional
    public ClientResponse create(ClientRequest request) {
        if (repository.existsByDocument(request.getDocument())) {
            throw new BusinessException("Document already registered");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        Client client = new Client();
        client.setClientType(request.getClientType());
        client.setDocument(request.getDocument());
        client.setLegalName(request.getLegalName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        client.setCreatedAt(LocalDateTime.now());

        Client savedClient = repository.save(client);

        accountService.createDefaultAccount(savedClient);

        return toResponse(savedClient);
    }

    public List<ClientResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ClientResponse findById(Integer id) {
        return toResponse(findClientById(id));
    }

    public ClientResponse update(Integer id, ClientRequest request) {
        Client client = findClientById(id);

        if (!client.getDocument().equals(request.getDocument()) && repository.existsByDocument(request.getDocument())) {
            throw new BusinessException("Document already registered");
        }
        if (!client.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        client.setClientType(request.getClientType());
        client.setDocument(request.getDocument());
        client.setLegalName(request.getLegalName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            client.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return toResponse(repository.save(client));
    }

    public void delete(Integer id) {
        Client client = findClientById(id);
        repository.delete(client);
    }

    public Client findClientById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getClientType(),
                client.getDocument(),
                client.getLegalName(),
                client.getEmail(),
                client.getPhone(),
                client.getCreatedAt()
        );
    }
}
