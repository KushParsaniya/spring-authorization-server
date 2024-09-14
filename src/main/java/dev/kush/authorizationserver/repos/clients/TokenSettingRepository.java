package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.TokenSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenSettingRepository extends JpaRepository<TokenSetting, Long> {
}