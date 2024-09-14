package dev.kush.authorizationserver.service.impl;

import dev.kush.authorizationserver.enums.AvailableClientAuthorizationMethod;
import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;
import dev.kush.authorizationserver.repos.clients.ClientAuthMethodRepository;
import dev.kush.authorizationserver.service.ClientAuthMethodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientAuthMethodServiceImpl implements ClientAuthMethodService {

    private final ClientAuthMethodRepository clientAuthMethodRepository;

    @Override
    public ClientAuthMethod getClientAuthMethodOrCreate(String clientAuthMethod) {
        return clientAuthMethodRepository.findByClientAuthMethodName(clientAuthMethod)
                .orElseGet(() -> ClientAuthMethod.builder()
                        .authorizationMethod(AvailableClientAuthorizationMethod.valueOf(clientAuthMethod))
                        .build());
    }
}
