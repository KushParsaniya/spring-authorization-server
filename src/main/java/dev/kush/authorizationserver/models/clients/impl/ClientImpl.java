package dev.kush.authorizationserver.models.clients.impl;

import dev.kush.authorizationserver.models.clients.entities.Client;
import dev.kush.authorizationserver.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientImpl extends RegisteredClient {

    private Client client;

    @Override
    public String getId() {
        return String.valueOf(client.getId());
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Instant getClientIdIssuedAt() {
        return client.getIssuedAt();
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public Instant getClientSecretExpiresAt() {
        return client.getExpiresAt();
    }

    @Override
    public String getClientName() {
        return client.getClientName();
    }

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethods() {
        return client.getClientAuthMethods()
                .stream()
                .map(MapperUtils::mapClientAuthMethod)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
        return client.getGrantTypes()
                .stream()
                .map(MapperUtils::mapGrantType)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRedirectUris() {
        return client.getRedirectUris().stream()
                .map(MapperUtils::mapRedirectUri)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getPostLogoutRedirectUris() {
        return Set.of();
    }

    @Override
    public Set<String> getScopes() {
        return client.getScopes().stream()
                .map(MapperUtils::mapScope)
                .collect(Collectors.toSet());
    }

    @Override
    public ClientSettings getClientSettings() {
        return MapperUtils.mapClientSettings(client.getClientSetting());
    }

    @Override
    public TokenSettings getTokenSettings() {
        return MapperUtils.mapTokenSettings(client.getTokenSetting());
    }
}
