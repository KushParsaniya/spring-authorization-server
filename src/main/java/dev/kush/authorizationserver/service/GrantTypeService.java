package dev.kush.authorizationserver.service;

import dev.kush.authorizationserver.models.clients.entities.GrantType;

public interface GrantTypeService {

    GrantType getGrantTypeOrCreate(String grantType);
}
