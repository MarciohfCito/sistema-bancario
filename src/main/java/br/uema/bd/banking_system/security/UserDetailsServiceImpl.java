package br.uema.bd.banking_system.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.uema.bd.banking_system.entity.Client;
import br.uema.bd.banking_system.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String document) throws UsernameNotFoundException {
        Client client = clientRepository.findByDocument(document)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found: " + document));

        return new User(
                client.getDocument(),
                client.getPasswordHash(),
                Collections.emptyList()
        );
    }
}
