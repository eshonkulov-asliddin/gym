package dev.gym.repository.datasource;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class InMemoryDatabaseConfig {
    @Bean
    public Map<String, Integer> usernameStore(){
        return new HashMap<>();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        return Persistence.createEntityManagerFactory("GYM");
    }
}
