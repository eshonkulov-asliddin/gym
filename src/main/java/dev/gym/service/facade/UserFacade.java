package dev.gym.service.facade;

import java.util.Optional;

public interface UserFacade<T, K> extends Facade<T, K> {

    boolean deleteById(K id, String username, String password);

    Optional<T> getByUsername(String username, String password);

    void deleteByUsername(String username, String password);

    void updatePassword(String username, String newPassword, String oldPassword);

    void setActiveStatus(String username, String password, boolean activeStatus);

    void login(String username, String password);

}
