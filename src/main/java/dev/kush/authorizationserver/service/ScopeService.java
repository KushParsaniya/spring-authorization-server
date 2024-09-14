package dev.kush.authorizationserver.service;

import dev.kush.authorizationserver.models.clients.entities.Scope;

public interface ScopeService {

    Scope getScopeOrCreate(String scope);
}
