package dev.kush.authorizationserver.models.clients.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "redirect_uris")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedirectUri {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long redirectUriId;

    @Column(nullable = false)
    private String uri;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;


}
