package dev.kush.authorizationserver.models.clients.impl;

import dev.kush.authorizationserver.models.clients.entities.Client;
import dev.kush.authorizationserver.utils.MapperUtilsNew;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Getter
@Setter
public class ClientImpl extends RegisteredClient {

    private Client client;

    private MapperUtilsNew mapperUtilsNew;

    @Autowired
    public ClientImpl(MapperUtilsNew mapperUtilsNew) {
        this.mapperUtilsNew = mapperUtilsNew;
    }

    public ClientImpl(Client client) {
        this.client = client;
    }

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
                .map(mapperUtilsNew::mapClientAuthMethod)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
        return client.getGrantTypes()
                .stream()
                .map(mapperUtilsNew::mapGrantType)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRedirectUris() {
        return client.getRedirectUris().stream()
                .map(mapperUtilsNew::mapRedirectUri)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getPostLogoutRedirectUris() {
        return Set.of();
    }

    @Override
    public Set<String> getScopes() {
        return client.getScopes().stream()
                .map(mapperUtilsNew::mapScope)
                .collect(Collectors.toSet());
    }

    @Override
    public ClientSettings getClientSettings() {
        return mapperUtilsNew.mapClientSettings(client.getClientSetting());
    }

    @Override
    public TokenSettings getTokenSettings() {
        return mapperUtilsNew.mapTokenSettings(client.getTokenSetting());
    }
}
