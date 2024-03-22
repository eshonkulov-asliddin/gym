package dev.gym.cucumber.steps;

import dev.gym.cucumber.util.TraineeHttpClient;
import dev.gym.service.dto.RegisterTraineeDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import static dev.gym.cucumber.util.Utils.ADDRESS;
import static dev.gym.cucumber.util.Utils.DATE_OF_BIRTH;
import static dev.gym.cucumber.util.Utils.FIRSTNAME;
import static dev.gym.cucumber.util.Utils.LASTNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTrainee {

    @Autowired
    private TraineeHttpClient traineeHttpClient;
    private RegisterTraineeDto registerTraineeDto;
    private int registerTraineeStatusCode;

    @Given("valid trainee details")
    public void valid_trainee_details() {
        registerTraineeDto = createValidTraineeDto();
    }

    @When("register a new trainee")
    public void register_a_new_trainee() {
        registerTraineeStatusCode = traineeHttpClient.registerTrainee(registerTraineeDto);
    }

    @Then("create new trainee")
    public void create_new_trainee() {
        assertEquals(201, registerTraineeStatusCode);
    }

    @Given("invalid trainee details")
    public void invalid_trainee_details() {
        registerTraineeDto = createInvalidTraineeDto();
    }

    @When("create a new trainee")
    public void create_a_new_trainee() {
        registerTraineeStatusCode = traineeHttpClient.registerTrainee(registerTraineeDto);
    }

    @Then("return {int} status code")
    public void return_wanted_status_code(int statusCode) {
        Assertions.assertEquals(statusCode, registerTraineeStatusCode);
    }

    private RegisterTraineeDto createInvalidTraineeDto() {
        return new RegisterTraineeDto(null, null, null, null);
    }

    private RegisterTraineeDto createValidTraineeDto() {
        return new RegisterTraineeDto(FIRSTNAME, LASTNAME, DATE_OF_BIRTH, ADDRESS);
    }
}
