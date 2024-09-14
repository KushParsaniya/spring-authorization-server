package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("""
                select c from Client c
                join fetch c.clientSetting cs
                join fetch c.tokenSetting ts
                join fetch c.scopes s
                join fetch c.clientAuthMethods cam
                join fetch c.redirectUris ru
                join fetch c.grantTypes gt
                where c.clientId = :clientId
            """)
    Optional<Client> findByClientId(String clientId);
}