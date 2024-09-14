package dev.kush.authorizationserver.utils;

import dev.kush.authorizationserver.enums.AvailableGrantType;
import dev.kush.authorizationserver.models.clients.entities.Client;
import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;
import dev.kush.authorizationserver.models.clients.entities.ClientSetting;
import dev.kush.authorizationserver.models.clients.entities.GrantType;
import dev.kush.authorizationserver.models.clients.entities.RedirectUri;
import dev.kush.authorizationserver.models.clients.entities.Scope;
import dev.kush.authorizationserver.models.clients.entities.TokenSetting;
import dev.kush.authorizationserver.models.users.entties.User;
import dev.kush.authorizationserver.service.ClientAuthMethodService;
import dev.kush.authorizationserver.service.GrantTypeService;
import dev.kush.authorizationserver.service.ScopeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperUtilsNew {

    private final ClientAuthMethodService clientAuthMethodService;
    private final GrantTypeService grantTypeService;
    private final ScopeService scopeService;

    // Generic method to map custom to Spring
    private <T, R> R map(T source, java.util.function.Function<T, R> mapper) {
        return mapper.apply(source);
    }

    // Generic method to map Spring to custom
    private <T, R> T mapBack(R source, java.util.function.Function<R, T> mapper) {
        return mapper.apply(source);
    }

    public ClientAuthenticationMethod mapClientAuthMethod(ClientAuthMethod clientAuthMethod) {
        return map(clientAuthMethod, cam -> new ClientAuthenticationMethod(cam.getAuthorizationMethod().name()));
    }

    public ClientAuthMethod mapClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
//        return mapBack(clientAuthenticationMethod, cam -> ClientAuthMethod.builder()
//                .authorizationMethod(AvailableClientAuthorizationMethod.valueOf(cam.getValue()))
//                .build());
        return mapBack(clientAuthenticationMethod, cam -> clientAuthMethodService.getClientAuthMethodOrCreate(cam.getValue()));
    }

    public ClientAuthMethod mapClientAuthMethodFromString(String clientAuthMethod) {
//        return mapBack(clientAuthMethod, cam -> ClientAuthMethod.builder()
//                .authorizationMethod(AvailableClientAuthorizationMethod.valueOf(cam))
//                .build());

        return clientAuthMethodService.getClientAuthMethodOrCreate(clientAuthMethod);
    }

    public AuthorizationGrantType mapGrantType(GrantType grantType) {
        return map(grantType, gt -> new AuthorizationGrantType(gt.getGrantType().name()));
    }

    public GrantType mapAuthorizationGrantType(AuthorizationGrantType authorizationGrantType) {
//        return mapBack(authorizationGrantType, agt -> GrantType.builder()
//                .grantType(AvailableGrantType.valueOf(agt.getValue()))
//                .build());
        return mapBack(authorizationGrantType, agt -> grantTypeService.getGrantTypeOrCreate(agt.getValue()));
    }

    public GrantType mapGrantTypeFromString(String grantType) {
//        return mapBack(grantType, gt -> GrantType.builder()
//                .grantType(AvailableGrantType.valueOf(gt))
//                .build());
        return mapBack(grantType, gt -> GrantType.builder().grantType(AvailableGrantType.valueOf(gt)).build());
    }

    public ClientSettings mapClientSettings(ClientSetting clientSetting) {
        return map(clientSetting, cs -> ClientSettings.builder()
                .requireProofKey(cs.isRequireProofKey())
                .requireAuthorizationConsent(cs.isRequireConsent())
                .build());
    }

    public ClientSetting mapClientSetting(ClientSettings clientSettings) {
        return mapBack(clientSettings, cs -> ClientSetting.builder()
                .requireProofKey(cs.isRequireProofKey())
                .requireConsent(cs.isRequireAuthorizationConsent())
                .build());
    }

    public TokenSettings mapTokenSettings(TokenSetting tokenSetting) {
        return map(tokenSetting, ts -> TokenSettings.builder()
                .authorizationCodeTimeToLive(Duration.of(ts.getAuthorizationCodeTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .accessTokenTimeToLive(Duration.of(ts.getAccessTokenTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .refreshTokenTimeToLive(Duration.of(ts.getRefreshTokenTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .deviceCodeTimeToLive(Duration.of(ts.getDeviceCodeTtl(), TimeUnit.SECONDS.toChronoUnit()))
                .reuseRefreshTokens(ts.isReuseRefreshToken())
                .build());
    }

    public TokenSetting mapTokenSetting(TokenSettings tokenSettings) {
        return mapBack(tokenSettings, ts -> TokenSetting.builder()
                .authorizationCodeTtl(ts.getAuthorizationCodeTimeToLive().getSeconds())
                .accessTokenTtl(ts.getAccessTokenTimeToLive().getSeconds())
                .refreshTokenTtl(ts.getRefreshTokenTimeToLive().getSeconds())
                .deviceCodeTtl(ts.getDeviceCodeTimeToLive().getSeconds())
                .isReuseRefreshToken(ts.isReuseRefreshTokens())
                .build());
    }

    public String mapRedirectUri(RedirectUri redirectUri) {
        return map(redirectUri, RedirectUri::getUri);
    }

    public RedirectUri mapRedirectUriFromString(String uri) {
        return mapBack(uri, u -> RedirectUri.builder().uri(u).build());
    }

    public String mapScope(Scope scope) {
        return map(scope, Scope::getScope);
    }

    public Scope mapScopeFromString(String scope) {
//        return mapBack(scope, s -> Scope.builder().scope(s).build());
        return scopeService.getScopeOrCreate(scope);
    }

    public GrantedAuthority mapGrantedAuthority(Scope scope) {
        return map(scope, s -> new SimpleGrantedAuthority("SCOPE_" + s.getScope()));
    }

    public Scope mapScopeFromGrantedAuthority(GrantedAuthority grantedAuthority) {
//        return mapBack(grantedAuthority, ga ->
//                Scope.builder()
//                        .scope(ga.getAuthority())
//                        .build()
//        );

        return mapBack(grantedAuthority, ga -> scopeService.getScopeOrCreate(ga.getAuthority()));
    }

    public Client mapClient(RegisteredClient registeredClient) {
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
                        .map(this::mapClientAuthenticationMethod)
                        .collect(Collectors.toSet())
        );
        client.addGrantTypes(
                registeredClient.getAuthorizationGrantTypes()
                        .stream()
                        .map(this::mapAuthorizationGrantType)
                        .collect(Collectors.toSet())
        );
        client.addRedirectUris(
                registeredClient.getRedirectUris()
                        .stream()
                        .map(this::mapRedirectUriFromString)
                        .collect(Collectors.toSet())
        );
        client.addScopes(
                registeredClient.getScopes()
                        .stream()
                        .map(this::mapScopeFromString)
                        .collect(Collectors.toSet())
        );
        return client;
    }


    // spring to custom
    public User mapUser(UserDetails userDetails) {
        return map(userDetails, ud -> {
            User user = User.builder()
                    .userName(ud.getUsername())
                    .password(ud.getPassword())
                    .build();

            user.addScopes(
                    ud.getAuthorities()
                            .stream()
                            .map(this::mapScopeFromGrantedAuthority)
                            .collect(Collectors.toSet())
            );
            return user;
        });
    }
}