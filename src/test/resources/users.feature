Feature: Users can be created, retrieved, and deleted
  Scenario: client makes call to GET /users with an empty database
    When the client calls /users
    Then the client receives status code of 200
    And the client receives an empty array
  Scenario Outline: client makes call to POST /users with a first name, last name, and birthday
    When the client posts to /users with a first name: "<firstName>", last name: "<lastName>", and birthday : <birthday>
    Then the client receives status code of 200
    And the response has a firstName field matching: "<firstName>"

    Examples:
    | firstName | lastName  | birthday  |
    | Sean      | Franklin  | 1991      |
    | Todd      | Frank     | 1989      |