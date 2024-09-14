package dev.kush.authorizationserver.service.impl;

import dev.kush.authorizationserver.enums.AvailableGrantType;
import dev.kush.authorizationserver.models.clients.entities.GrantType;
import dev.kush.authorizationserver.repos.clients.GrantTypeRepository;
import dev.kush.authorizationserver.service.GrantTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GrantTypeServiceImpl implements GrantTypeService {

    private final GrantTypeRepository grantTypeRepository;

    @Override
    public GrantType getGrantTypeOrCreate(String grantType) {
        return grantTypeRepository.findByGrantTypeName(grantType)
                .orElseGet(() -> GrantType.builder()
                        .grantType(AvailableGrantType.valueOf(grantType))
                        .build());
    }
}
