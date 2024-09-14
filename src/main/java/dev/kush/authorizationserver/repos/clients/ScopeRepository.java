package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {

    @Query("SELECT s FROM Scope s WHERE s.scope = :scope")
    Optional<Scope> findByScopeName(String scope);
}