package dev.kush.authorizationserver.service;

import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;

public interface ClientAuthMethodService {

    ClientAuthMethod getClientAuthMethodOrCreate(String clientAuthMethod);
}
