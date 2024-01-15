package dev.gym.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component("random")
public class RandomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        double chance = ThreadLocalRandom.current().nextDouble();
        Health.Builder status = Health.up().withDetail("A random number", chance);
        if (chance > 0.9) {
            status = Health.down().withDetail("A random number", chance);
        }
        return status.build();
    }
}