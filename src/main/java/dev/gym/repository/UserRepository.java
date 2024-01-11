package dev.gym.repository;

import java.util.Optional;

public interface UserRepository<T, K> extends CrudRepository<T, K> {

    Optional<T> findByUsername(String username);

    void deleteByUsername(String username);

    void updatePassword(String username, String newPassword);

    void setActiveStatus(String username, boolean activeStatus);

    boolean existByUsername(String username);

}
