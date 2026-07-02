package br.uema.bd.banking_system.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.dto.LoginRequest;
import br.uema.bd.banking_system.dto.LoginResponse;
import br.uema.bd.banking_system.dto.RegisterRequest;
import br.uema.bd.banking_system.entity.Account;
import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.exception.BusinessException;
import br.uema.bd.banking_system.repository.ClientRepository;
import br.uema.bd.banking_system.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final CardService cardService;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getDocument(), request.getPassword())
        );

        Client client = clientRepository.findByDocument(request.getDocument())
                .orElseThrow(() -> new BusinessException("Client not found"));

        String token = tokenProvider.generateToken(client.getDocument(), client.getId());

        return new LoginResponse(token, client.getClientType(), client.getId(), client.getLegalName());
    }

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        if (clientRepository.existsByDocument(request.getDocument())) {
            throw new BusinessException("Document already registered");
        }
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        Client client = new Client();

        //pega os dados do cliente do request e seta no objeto client
        client.setClientType(request.getClientType());
        client.setDocument(request.getDocument());
        client.setLegalName(request.getLegalName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        client.setCreatedAt(LocalDateTime.now());

        //salva os dados do cliente no banco de dados
        Client savedClient = clientRepository.save(client);

        //cria a conta
        Account account = accountService.createDefaultAccount(savedClient);

        cardService.createDefaultCard(account);

        String token = tokenProvider.generateToken(
                savedClient.getDocument(),
                savedClient.getId()
        );

        return new LoginResponse(
                token,
                savedClient.getClientType(),
                savedClient.getId(),
                savedClient.getLegalName()
        );
    }
}
