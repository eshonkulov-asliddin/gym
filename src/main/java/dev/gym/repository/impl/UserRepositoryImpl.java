package dev.gym.repository.impl;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractUserRepository<User, Long> implements UserRepository<User, Long> {

    @Autowired
    public UserRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
}
