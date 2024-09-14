package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.GrantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantTypeRepository extends JpaRepository<GrantType, Long> {
}