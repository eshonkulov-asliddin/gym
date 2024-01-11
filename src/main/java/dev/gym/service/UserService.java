package dev.gym.service;

import java.util.Optional;

public interface UserService<T, K, E> extends CrudService<T, K, E> {

    Optional<T> getByUsername(String username);

    void deleteByUsername(String username);

    void updatePassword(String username, String newPassword);

    void setActiveStatus(String username, boolean activeStatus);

}
