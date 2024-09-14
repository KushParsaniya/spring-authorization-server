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
@Table(name = "token_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenSetting {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tokenSettingId;

    @Builder.Default
    private long authorizationCodeTtl = 300L;

    @Builder.Default
    private long accessTokenTtl = 3600L;

    @Builder.Default
    private long refreshTokenTtl = 86400L;

    @Builder.Default
    private long deviceCodeTtl = 300L;

    @Builder.Default
    private boolean isReuseRefreshToken = false;

}
