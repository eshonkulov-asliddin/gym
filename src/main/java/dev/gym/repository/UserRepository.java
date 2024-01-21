package dev.gym.repository;

import dev.gym.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository<T extends User, ID> extends JpaRepository<T, ID> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<T> findByUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    void updatePasswordByUsername(@Param("username") String username,
                                  @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.isActive = :active WHERE u.username = :username")
    void setActiveStatusByUsername(@Param("username") String username,
                                   @Param("active") boolean active);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

}
