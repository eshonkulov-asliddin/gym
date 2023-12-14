package dev.gym.repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository<T, L , K> extends CrudRepository<T, K> {

    Optional<T> findByUsername(String username);

    void deleteByUsername(String username);

    void updatePassword(K id, String newPassword);

    void setActiveStatus(K id, boolean status);

    List<L> findAllTrainingsByUsername(String username);

}
