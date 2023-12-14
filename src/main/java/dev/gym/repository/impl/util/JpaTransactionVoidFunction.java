package dev.gym.repository.impl.util;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;

public interface JpaTransactionVoidFunction extends Consumer<EntityManager> {
    /**
     * Before transaction completion function
     */
    default void beforeTransactionCompletion() {

    }

    /**
     * After transaction completion function
     */
    default void afterTransactionCompletion() {

    }
}
