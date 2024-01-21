package dev.gym.actuator;

import dev.gym.repository.UserRepository;
import dev.gym.repository.model.User;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class CustomMetrics {

    @Autowired
    private UserRepository<User, Long> userRepository;

    public Supplier<Number> fetchUsersCount() {
        return () -> userRepository.findAll().size();
    }

    public CustomMetrics(MeterRegistry registry) {
        Gauge.builder("users.count", fetchUsersCount())
                .description("Total number of users")
                .register(registry);
    }
}
