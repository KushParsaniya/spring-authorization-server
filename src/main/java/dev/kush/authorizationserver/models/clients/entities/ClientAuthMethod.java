package dev.kush.authorizationserver.models.clients.entities;

import dev.kush.authorizationserver.enums.AvailableClientAuthorizationMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client_auth_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientAuthMethod {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "client_auth_method_id")
    private Long clientAuthMethodId;


    @Enumerated(EnumType.STRING)
    private AvailableClientAuthorizationMethod authorizationMethod;
}
