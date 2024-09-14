package dev.kush.authorizationserver.service.impl;

import dev.kush.authorizationserver.models.clients.impl.ClientImpl;
import dev.kush.authorizationserver.repos.clients.ClientRepository;
import dev.kush.authorizationserver.service.ClientService;
import dev.kush.authorizationserver.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        clientRepository.save(MapperUtils.mapClient(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(id)
                .map(ClientImpl::new)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(ClientImpl::new)
                .orElse(null);
    }
}
