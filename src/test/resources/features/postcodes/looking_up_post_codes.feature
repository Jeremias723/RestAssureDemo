Feature: Looking up post codes

  Scenario Outline: Looking up US locations by post code
    When I look up a post code <Post Code> for country code <Country Code>
    Then the resulting location should be <Place Name> in <Country>
    Examples:
      | Post Code | Country Code | Country       | Place Name    |
      | 10000     | US           | United States | New York City |
      | 90210     | US           | United States | Beverly Hills |
      | 13001     | FR           | France        | Marseille 01  |

  Scenario Outline: I request all the information of all users.
    Given I set the base url <baseUrl>
    When I perform a <operation> to the endpoint <endpoint>
    Examples:
      | baseUrl               | endpoint | operation |
      | https://reqres.in/api | /users   | GET       |

  Scenario Outline: I create a new user
    Given I set the base url <baseUrl>
    When I perform a <operation> to the endpoint <endpoint> with the info <userName> and <userJob>
    Examples:
      | baseUrl               | endpoint | operation | userName  | userJob |
      | https://reqres.in/api | /users   | POST      | Alexander | Medic   |

  Scenario Outline: Edit a user
    Given I set the base url <baseUrl>
    When I perform a <operation> to the endpoint <endpoint> with the info <userName> and <userJob>
    Examples:
      | baseUrl               | endpoint | operation | userName | userJob   |
      | https://reqres.in/api | /users   | PUT       | Alexa    | Professor |

  Scenario Outline: Find a user by id
    Given I set the base url <baseUrl>
    When I perform a GET to find a user by id <id>
    Examples:
      | baseUrl               | id |
      | https://reqres.in/api | 7  |

  Scenario Outline: I delete the first user
    Given I set the base url <baseUrl>
    When I perform a <operation> to the endpoint <endpoint>
    Examples:
      | baseUrl               | endpoint | operation |
      | https://reqres.in/api | /users   | DELETE    |

  Scenario Outline: Create a user with a .json file
    Given I set the base url <baseUrl>
    When I perform a <operation> to the endpoint <endpoint> with the json <jsonPath>
    Examples:
      | baseUrl               | operation | endpoint | jsonPath                                     |
      | https://reqres.in/api | POST      | /users   | src/test/resources/jsons/createANewUser.json |

