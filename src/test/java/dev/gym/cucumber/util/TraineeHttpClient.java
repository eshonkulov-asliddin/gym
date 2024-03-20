package dev.gym.cucumber.util;

import dev.gym.controller.util.RestApiConst;
import dev.gym.service.dto.RegisterTraineeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class TraineeHttpClient {

    @Value("${serverUrl}")
    private String serverUrl;
    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate;

    public TraineeHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int registerTrainee(RegisterTraineeDto registerTraineeDto) {
        HttpEntity<RegisterTraineeDto> requestEntity = new HttpEntity<>(registerTraineeDto);
        return executePostRequest(getRegisterTraineeEndpoint(), requestEntity);
    }

    private int executePostRequest(String url, HttpEntity<RegisterTraineeDto> requestEntity) {
        try {
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class).getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getStatusCode().value();
        }
    }

    private String getRegisterTraineeEndpoint() {
        return serverUrl + ":" + port + RestApiConst.TRAINEE_API_ROOT_PATH + "/register";
    }
}
