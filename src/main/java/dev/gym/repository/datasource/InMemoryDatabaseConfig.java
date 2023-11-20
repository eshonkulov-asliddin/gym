package dev.gym.repository.datasource;

import dev.gym.model.Trainee;
import dev.gym.model.Trainer;
import dev.gym.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class InMemoryDatabaseConfig {

    @Bean
    public Map<Long, Trainee> traineeStore(){
        return new HashMap<>();
    }
    @Bean
    public Map<Long, Trainer> trainerStore(){
        return new HashMap<>();
    }
    @Bean
    public Map<Long, Training> trainingStore(){
        return new HashMap<>();
    }
    @Bean
    public Map<String, Integer> usernameStore(){
        return new HashMap<>();
    }
}
