package dev.kush.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class UserManagementConfig {

    @Bean
    UserDetailsManager userDetailsManager() {
        UserDetails u1 = User.withDefaultPasswordEncoder()
                .username("kush")
                .password("1234")
                .authorities("SCOPE_read")
                .build();

        UserDetails u2 = User.withDefaultPasswordEncoder()
                .username("abhi")
                .password("1234")
                .authorities("SCOPE_write","SCOPE_read")
                .build();

        return new InMemoryUserDetailsManager(u1,u2);
    }
}
