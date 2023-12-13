package dev.gym.repository.impl.util;

import jakarta.persistence.EntityManager;
import org.springframework.cglib.core.internal.Function;

public interface JpaTransactionFunction<T> extends Function<EntityManager, T> {
    /**
     * Before transaction completion function
     */
    default void beforeTransactionCompletion() {
        System.out.println("beforeTransactionCompletion");
    }

    /**
     * After transaction completion function
     */
    default void afterTransactionCompletion() {
        System.out.println("afterTransactionCompletion");
    }
}
