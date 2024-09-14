package dev.kush.authorizationserver.models.clients.dtos;

import java.util.Set;

public record CreateClientDto(
        String clientId,
        String clientName,
        Set<String> grantTypes,
        Set<String> scopes,
        Set<String> redirectUris,
        Set<String> clientAuthMethods,
        boolean requiredProofKey,
        boolean requireConsent
) {
}
