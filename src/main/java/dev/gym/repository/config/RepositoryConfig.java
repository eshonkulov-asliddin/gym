package dev.gym.repository.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "dev.gym.repository")
@PropertySource("classpath:application.properties")
public class RepositoryConfig {

}
