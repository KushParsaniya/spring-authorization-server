package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScopeRepository extends JpaRepository<Scope, Long> {
}