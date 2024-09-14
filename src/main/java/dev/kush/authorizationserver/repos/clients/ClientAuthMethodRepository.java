package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.ClientAuthMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientAuthMethodRepository extends JpaRepository<ClientAuthMethod, Long> {

    @Query("SELECT c FROM ClientAuthMethod c WHERE c.authorizationMethod = :clientAuthMethod")
    Optional<ClientAuthMethod> findByClientAuthMethodName(String clientAuthMethod);
}