package dev.kush.authorizationserver;

import dev.kush.authorizationserver.enums.AvailableClientAuthorizationMethod;
import dev.kush.authorizationserver.enums.AvailableGrantType;
import dev.kush.authorizationserver.models.clients.entities.Client;
import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;
import dev.kush.authorizationserver.models.clients.entities.ClientSetting;
import dev.kush.authorizationserver.models.clients.entities.GrantType;
import dev.kush.authorizationserver.models.clients.entities.RedirectUri;
import dev.kush.authorizationserver.models.clients.entities.Scope;
import dev.kush.authorizationserver.models.clients.entities.TokenSetting;
import dev.kush.authorizationserver.repos.clients.ClientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    //        @Bean
    ApplicationRunner applicationRunner(ClientRepository clientRepository) {
        return args -> {

//            var redirectUri =

            var client = Client.builder()
                    .clientId("client-1")
                    .clientSecret("secret-1")
                    .clientName("Demo Client")
                    .clientSetting(
                            ClientSetting.builder().build())
                    .tokenSetting(
                            TokenSetting.builder().build()
                    )
                    .scopes(Set.of(
                            Scope.builder()
                                    .scope("openid")
                                    .build(),
                            Scope.builder()
                                    .scope("read")
                                    .build(),
                            Scope.builder()
                                    .scope("write")
                                    .build()

                    ))
                    .clientAuthMethods(Set.of(
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.client_secret_basic)
                                    .build(),
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.client_secret_jwt)
                                    .build(),
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.client_secret_post)
                                    .build(),
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.none)
                                    .build(),
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.tls_client_auth)
                                    .build(),
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.private_key_jwt)
                                    .build(),
                            ClientAuthMethod.builder()
                                    .authorizationMethod(AvailableClientAuthorizationMethod.self_signed_tls_client_auth)
                                    .build()
                    ))
                    .grantTypes(
                            Set.of(
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.authorization_code)
                                            .build(),
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.client_credentials)
                                            .build(),
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.device_code)
                                            .build(),
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.refresh_token)
                                            .build(),
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.jwt_bearer)
                                            .build(),
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.password)
                                            .build(),
                                    GrantType.builder()
                                            .grantType(AvailableGrantType.token_exchange)
                                            .build()
                            )
                    )
                    .build();

            client.addRedirectUris(Set.of(
                    RedirectUri.builder()
                            .uri("http://localhost:8080")
                            .build(),
                    RedirectUri.builder()
                            .uri("http://localhost:9091/login/oauth2/code/client-1")
                            .build()
            ));
            clientRepository.save(client);
        };
    }
}
