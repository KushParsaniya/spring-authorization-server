package dev.kush.authorizationserver.repos.clients;

import dev.kush.authorizationserver.models.clients.entities.GrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GrantTypeRepository extends JpaRepository<GrantType, Long> {

    @Query("SELECT g FROM GrantType g WHERE g.grantType = :grantType")
    Optional<GrantType> findByGrantTypeName(String grantType);
}