Feature: Users can be created, retrieved, and deleted
  Scenario: client makes call to GET /users with an empty database
    When the client calls /users
    Then the client receives status code of 200
    And the client receives an empty array
  Scenario: client makes call to POST /users with a first name, last name, and birthday
    When the client posts to /users
    Then the client receives status code of 200
    And the response has a valid firstName field