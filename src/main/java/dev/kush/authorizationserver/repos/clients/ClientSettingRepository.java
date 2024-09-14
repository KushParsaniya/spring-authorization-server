package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.ClientSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientSettingRepository extends JpaRepository<ClientSetting, Long> {
}