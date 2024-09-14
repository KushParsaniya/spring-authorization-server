package dev.kush.authorizationserver.service.impl;

import dev.kush.authorizationserver.models.clients.impl.ClientImpl;
import dev.kush.authorizationserver.repos.clients.ClientRepository;
import dev.kush.authorizationserver.service.SecuredClientService;
import dev.kush.authorizationserver.utils.MapperUtilsNew;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecuredClientServiceImpl implements SecuredClientService {

    private final ClientRepository clientRepository;
    private final MapperUtilsNew mapperUtilsNew;

    @Override
    public void save(RegisteredClient registeredClient) {
        clientRepository.save(mapperUtilsNew.mapClient(registeredClient));
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
