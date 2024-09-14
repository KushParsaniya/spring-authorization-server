package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.RedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedirectUriRepository extends JpaRepository<RedirectUri, Long> {
}