package dev.kush.authorizationserver.models.clients.impl;

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

public class MapperUtilsNew {

    // Generic method to map custom to Spring
    private static <T, R> R map(T source, java.util.function.Function<T, R> mapper) {
        return mapper.apply(source);
    }

    // Generic method to map Spring to custom
    private static <T, R> T mapBack(R source, java.util.function.Function<R, T> mapper) {
        return mapper.apply(source);
    }

    public static ClientAuthenticationMethod mapClientAuthMethod(ClientAuthMethod clientAuthMethod) {
        return map(clientAuthMethod, cam -> new ClientAuthenticationMethod(cam.getAuthorizationMethod().name()));
    }

    public static ClientAuthMethod mapClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
        return mapBack(clientAuthenticationMethod, cam -> ClientAuthMethod.builder()
                .authorizationMethod(AvailableClientAuthorizationMethod.valueOf(cam.getValue()))
                .build());
    }

    public static AuthorizationGrantType mapGrantType(GrantType grantType) {
        return map(grantType, gt -> new AuthorizationGrantType(gt.getGrantType().name()));
    }

    public static GrantType mapAuthorizationGrantType(AuthorizationGrantType authorizationGrantType) {
        return mapBack(authorizationGrantType, agt -> GrantType.builder()
                .grantType(AvailableGrantType.valueOf(agt.getValue()))
                .build());
    }

    public static ClientSettings mapClientSettings(ClientSetting clientSetting) {
        return map(clientSetting, cs -> ClientSettings.builder()
                .requireProofKey(cs.isRequireProofKey())
                .requireAuthorizationConsent(cs.isRequireConsent())
                .build());
    }

    public static ClientSetting mapClientSetting(ClientSettings clientSettings) {
        return mapBack(clientSettings, cs -> ClientSetting.builder()
                .requireProofKey(cs.isRequireProofKey())
                .requireConsent(cs.isRequireAuthorizationConsent())
                .build());
    }

    public static TokenSettings mapTokenSettings(TokenSetting tokenSetting) {
        return map(tokenSetting, ts -> TokenSettings.builder()
                .authorizationCodeTimeToLive(Duration.of(ts.getAuthorizationCodeTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .accessTokenTimeToLive(Duration.of(ts.getAccessTokenTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .refreshTokenTimeToLive(Duration.of(ts.getRefreshTokenTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .deviceCodeTimeToLive(Duration.of(ts.getDeviceCodeTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .reuseRefreshTokens(ts.isReuseRefreshToken())
                .build());
    }

    public static TokenSetting mapTokenSetting(TokenSettings tokenSettings) {
        return mapBack(tokenSettings, ts -> TokenSetting.builder()
                .authorizationCodeTtl(ts.getAuthorizationCodeTimeToLive().getSeconds())
                .accessTokenTtl(ts.getAccessTokenTimeToLive().getSeconds())
                .refreshTokenTtl(ts.getRefreshTokenTimeToLive().getSeconds())
                .deviceCodeTtl(ts.getDeviceCodeTimeToLive().getSeconds())
                .isReuseRefreshToken(ts.isReuseRefreshTokens())
                .build());
    }

    public static String mapRedirectUri(RedirectUri redirectUri) {
        return map(redirectUri, RedirectUri::getUri);
    }

    public static RedirectUri mapRedirectUriFromString(String uri) {
        return mapBack(uri, u -> RedirectUri.builder().uri(u).build());
    }

    public static String mapScope(Scope scope) {
        return map(scope, Scope::getScope);
    }

    public static Scope mapScopeFromString(String scope) {
        return mapBack(scope, s -> Scope.builder().scope(s).build());
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
                        .map(MapperUtilsNew::mapClientAuthenticationMethod)
                        .collect(Collectors.toSet())
        );
        client.addGrantTypes(
                registeredClient.getAuthorizationGrantTypes()
                        .stream()
                        .map(MapperUtilsNew::mapAuthorizationGrantType)
                        .collect(Collectors.toSet())
        );
        client.addRedirectUris(
                registeredClient.getRedirectUris()
                        .stream()
                        .map(MapperUtilsNew::mapRedirectUriFromString)
                        .collect(Collectors.toSet())
        );
        client.addScopes(
                registeredClient.getScopes()
                        .stream()
                        .map(MapperUtilsNew::mapScopeFromString)
                        .collect(Collectors.toSet())
        );

        return client;
    }
}