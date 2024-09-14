package dev.kush.authorizationserver.service.impl;

import dev.kush.authorizationserver.models.clients.entities.Scope;
import dev.kush.authorizationserver.repos.clients.ScopeRepository;
import dev.kush.authorizationserver.service.ScopeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScopeServiceImpl implements ScopeService {

    private final ScopeRepository scopeRepository;

    @Override
    public Scope getScopeOrCreate(String scope) {
        return scopeRepository.findByScopeName(scope)
                .orElseGet(() -> Scope.builder()
                        .scope(scope)
                        .build());
    }
}
