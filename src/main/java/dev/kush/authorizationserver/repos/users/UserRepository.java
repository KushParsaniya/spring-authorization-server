package dev.kush.authorizationserver.repos.users;

import dev.kush.authorizationserver.models.users.entties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u from User u join fetch u.scopes s
            where u.userName = :userName and u.isDeleted = 0
            """)
    Optional<User> findByUserName(String userName);

    @Query("select count(1) > 0 from User u where u.userName = :userName and u.isDeleted = 0")
    boolean existByUserName(String userName);

    @Modifying
    @Query("""
            update User u set u.isDeleted = 1, u.updatedAt = current timestamp
            where u.userName = :userName and u.isDeleted = 0
            """)
    int deleteUserByUserName(String userName);

    @Modifying
    @Query("""
            update User u set u.password = :newPassword, u.updatedAt = current timestamp
            where u.userName = :userName and u.isDeleted = 0
            """)
    int updatePasswordByUserName(String userName, String newPassword);

}