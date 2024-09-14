package dev.kush.authorizationserver.models.clients.entities;

import dev.kush.authorizationserver.enums.AvailableGrantType;
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
@Table(name = "grant_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrantType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long grantTypeId;

    @Enumerated(EnumType.STRING)
    private AvailableGrantType grantType;
}
