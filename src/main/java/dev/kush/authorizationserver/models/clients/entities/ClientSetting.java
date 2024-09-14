package dev.kush.authorizationserver.models.clients.entities;

import jakarta.persistence.Entity;
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
@Table(name = "client_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientSetting {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long clientSettingId;

    @Builder.Default
    private boolean requireConsent = true;

    @Builder.Default
    private boolean requireProofKey = true;
}
