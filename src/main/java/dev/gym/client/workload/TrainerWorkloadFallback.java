package dev.gym.client.workload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrainerWorkloadFallback implements TrainerWorkloadClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerWorkloadFallback.class);

    @Override
    public void notifyWorkloadService(String authToken, TrainerWorkload trainerWorkload) {
        LOGGER.info("trainer-workload service crashed");
    }
}
