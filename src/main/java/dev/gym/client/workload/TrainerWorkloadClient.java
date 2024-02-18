package dev.gym.client.workload;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "workload-service", url = "localhost:8082", fallback = TrainerWorkloadFallback.class)
public interface TrainerWorkloadClient {

    @PostMapping("/workload-service/trainers/workload")
    void notifyWorkloadService(@RequestHeader("Authorization") String authToken, @RequestBody TrainerWorkload trainerWorkload);

}
