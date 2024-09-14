package dev.kush.authorizationserver.utils;

import dev.kush.authorizationserver.enums.AvailableClientAuthorizationMethod;
import dev.kush.authorizationserver.enums.AvailableGrantType;
import dev.kush.authorizationserver.models.clients.entities.Client;
import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;
import dev.kush.authorizationserver.models.clients.entities.ClientSetting;
import dev.kush.authorizationserver.models.clients.entities.GrantType;
import dev.kush.authorizationserver.models.clients.entities.RedirectUri;
import dev.kush.authorizationserver.models.clients.entities.Scope;
import dev.kush.authorizationserver.models.clients.entities.TokenSetting;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MapperUtils {

    // custom to spring
    public static ClientAuthenticationMethod mapClientAuthMethod(ClientAuthMethod clientAuthMethod) {
        return new ClientAuthenticationMethod(clientAuthMethod.getAuthorizationMethod().name());
    }

    // spring to custom
    public static ClientAuthMethod mapClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
        return ClientAuthMethod.builder()
                .authorizationMethod(AvailableClientAuthorizationMethod.valueOf(clientAuthenticationMethod.getValue()))
                .build();
    }

    // custom to spring
    public static AuthorizationGrantType mapGrantType(GrantType grantType) {
        return new AuthorizationGrantType(grantType.getGrantType().name());
    }

    // spring to custom
    public static GrantType mapAuthorizationGrantType(AuthorizationGrantType authorizationGrantType) {
        return GrantType.builder()
                .grantType(AvailableGrantType.valueOf(authorizationGrantType.getValue()))
                .build();
    }

    // custom to spring
    public static ClientSettings mapClientSettings(ClientSetting clientSetting) {
        return ClientSettings.builder()
                .requireProofKey(clientSetting.isRequireProofKey())
                .requireAuthorizationConsent(clientSetting.isRequireConsent())
                .build();
    }

    // spring to custom
    public static ClientSetting mapClientSetting(ClientSettings clientSettings) {
        return ClientSetting.builder()
                .requireProofKey(clientSettings.isRequireProofKey())
                .requireConsent(clientSettings.isRequireAuthorizationConsent())
                .build();
    }

    // custom to spring
    public static TokenSettings mapTokenSettings(TokenSetting tokenSetting) {
        return TokenSettings.builder()
                .authorizationCodeTimeToLive(Duration.of(tokenSetting.getAuthorizationCodeTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .accessTokenTimeToLive(Duration.of(tokenSetting.getAccessTokenTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .refreshTokenTimeToLive(Duration.of(tokenSetting.getRefreshTokenTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .deviceCodeTimeToLive(Duration.of(tokenSetting.getDeviceCodeTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .reuseRefreshTokens(tokenSetting.isReuseRefreshToken())
                .build();
    }

    // spring to custom
    public static TokenSetting mapTokenSetting(TokenSettings tokenSettings) {
        return TokenSetting.builder()
                .authorizationCodeTtl(tokenSettings.getAuthorizationCodeTimeToLive().getSeconds())
                .accessTokenTtl(tokenSettings.getAccessTokenTimeToLive().getSeconds())
                .refreshTokenTtl(tokenSettings.getRefreshTokenTimeToLive().getSeconds())
                .deviceCodeTtl(tokenSettings.getDeviceCodeTimeToLive().getSeconds())
                .isReuseRefreshToken(tokenSettings.isReuseRefreshTokens())
                .build();
    }

    // custom to spring
    public static String mapRedirectUri(RedirectUri redirectUri) {
        return redirectUri.getUri();
    }

    // spring to custom
    public static RedirectUri mapRedirectUriFromString(String uri) {
        return RedirectUri.builder()
                .uri(uri)
                .build();
    }

    // custom to spring
    public static String mapScope(Scope scope) {
        return scope.getScope();
    }

    // spring to custom
    public static Scope mapScopeFromString(String scope) {
        return Scope.builder()
                .scope(scope)
                .build();
    }

    public static Client mapClient(RegisteredClient registeredClient) {
        var client = Client.builder()
                .id(Long.valueOf(registeredClient.getId()))
                .clientId(registeredClient.getClientId())
                .clientSecret(registeredClient.getClientSecret())
                .clientName(registeredClient.getClientName())
                .issuedAt(registeredClient.getClientIdIssuedAt())
                .expiresAt(registeredClient.getClientSecretExpiresAt())
                .clientSetting(mapClientSetting(registeredClient.getClientSettings()))
                .tokenSetting(mapTokenSetting(registeredClient.getTokenSettings()))
                .build();

        client.addClientAuthorizationMethods(
                registeredClient.getClientAuthenticationMethods()
                        .stream()
                        .map(MapperUtils::mapClientAuthenticationMethod)
                        .collect(Collectors.toSet()
                        ));
        client.addGrantTypes(
                registeredClient.getAuthorizationGrantTypes()
                        .stream()
                        .map(MapperUtils::mapAuthorizationGrantType)
                        .collect(Collectors.toSet())
        );
        client.addRedirectUris(
                registeredClient.getRedirectUris()
                        .stream()
                        .map(MapperUtils::mapRedirectUriFromString)
                        .collect(Collectors.toSet())
        );
        client.addScopes(
                registeredClient.getScopes()
                        .stream()
                        .map(MapperUtils::mapScopeFromString)
                        .collect(Collectors.toSet())
        );

        return client;

    }
}
