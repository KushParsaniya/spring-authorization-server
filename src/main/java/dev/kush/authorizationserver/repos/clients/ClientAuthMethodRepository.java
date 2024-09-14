package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAuthMethodRepository extends JpaRepository<ClientAuthMethod, Long> {
}