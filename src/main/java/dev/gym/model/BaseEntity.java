package dev.gym.model;

public interface BaseEntity<K> {
    K getId();
    void setId(K id);
}
