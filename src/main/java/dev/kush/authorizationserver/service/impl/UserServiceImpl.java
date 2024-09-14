package dev.kush.authorizationserver.service.impl;

import dev.kush.authorizationserver.models.users.entties.User;
import dev.kush.authorizationserver.models.users.impl.SecuredUser;
import dev.kush.authorizationserver.repos.users.UserRepository;
import dev.kush.authorizationserver.service.UserService;
import dev.kush.authorizationserver.utils.MapperUtilsNew;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperUtilsNew mapperUtilsNew;

    @Override
    public void createUser(UserDetails userDetails) {

        if (userRepository.existByUserName(userDetails.getUsername())) {
            throw new EntityExistsException("user with this username already exists.");
        }

        User user = mapperUtilsNew.mapUser(userDetails);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        // TODO: Password can't be updated here also username can't be updated.
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUserByUserName(username);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found."));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Old password doesn't matches.");
        }

        userRepository.updatePasswordByUserName(username, passwordEncoder.encode(newPassword));
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: extract all thrown error in Constant.
        return userRepository.findByUserName(username)
                .map(SecuredUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }
}
