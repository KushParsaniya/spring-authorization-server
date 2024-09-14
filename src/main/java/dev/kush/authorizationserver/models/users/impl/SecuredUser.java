package dev.kush.authorizationserver.models.users.impl;

import dev.kush.authorizationserver.models.users.entties.User;
import dev.kush.authorizationserver.utils.MapperUtilsNew;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class SecuredUser implements UserDetails {

    private User user;
    private MapperUtilsNew mapperUtilsNew;

    @Autowired
    public SecuredUser(MapperUtilsNew mapperUtilsNew) {
        this.mapperUtilsNew = mapperUtilsNew;
    }

    public SecuredUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getScopes().stream()
                .map(mapperUtilsNew::mapGrantedAuthority)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
