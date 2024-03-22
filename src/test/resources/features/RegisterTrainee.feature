Feature: Register Trainee

  Scenario: Successful trainee registration
    Given valid trainee details
    When register a new trainee
    Then create new trainee

  Scenario: Unsuccessful trainee registration
    Given invalid trainee details
    When create a new trainee
    Then return 401 status code

