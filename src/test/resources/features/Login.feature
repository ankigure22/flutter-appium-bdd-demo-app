@login-feature
Feature: Login scenarios
#@test

  Scenario Outline: Login with invalid user name
    When I enter username as "<username>"
    And I enter password as "<password>"
    And I login
    Then login should fail with an error "<err>"

    Examples:
      | username        | password     | err                                                          |
      | problem_user    | zzz           | Username and password do not match any user in this service. |