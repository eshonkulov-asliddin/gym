package dev.gym.client.workload;

import feign.Headers;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "workload-service", fallback = TrainerWorkloadFallback.class)
public interface TrainerWorkloadClient {

    String AUTH_TOKEN = "Authorization";

    @Headers("Content-Type: application/json")
    @PostMapping("/workload-service/trainers/workload")
    void notifyWorkloadService(@RequestHeader(AUTH_TOKEN) String bearerToken,
                               @RequestBody @Valid TrainerWorkload trainerWorkload);

}
