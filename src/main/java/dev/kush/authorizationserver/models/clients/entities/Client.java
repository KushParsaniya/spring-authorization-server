package dev.kush.authorizationserver.models.clients.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    private String clientName;

    @Builder.Default
    private Instant issuedAt = Instant.now();

    @Builder.Default
    private Instant expiresAt = LocalDateTime.now().plusYears(100).toInstant(ZoneOffset.UTC);

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_grant_types",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "grant_type_id")
    )
    @Builder.Default
    private Set<GrantType> grantTypes = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "join_client_auth_methods",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "client_auth_method_id")
    )
    @Builder.Default
    private Set<ClientAuthMethod> clientAuthMethods = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = {PERSIST, MERGE, REMOVE}, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<RedirectUri> redirectUris = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_scopes",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    @Builder.Default
    private Set<Scope> scopes = new HashSet<>();

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE})
    private ClientSetting clientSetting;

    @OneToOne(cascade = {PERSIST, MERGE, REMOVE})
    private TokenSetting tokenSetting;


    public void addRedirectUris(Set<RedirectUri> redirectUriSet) {
        redirectUris.addAll(redirectUriSet);
        redirectUriSet.forEach(redirectUri -> redirectUri.setClient(this));
    }

    public void addScopes(Set<Scope> scopeSet) {
        scopes.addAll(scopeSet);
    }

    public void addGrantTypes(Set<GrantType> grantTypeSet) {
        grantTypes.addAll(grantTypeSet);
    }

    public void addClientAuthorizationMethods(Set<ClientAuthMethod> clientAuthMethodSet) {
        clientAuthMethods.addAll(clientAuthMethodSet);
    }
}
