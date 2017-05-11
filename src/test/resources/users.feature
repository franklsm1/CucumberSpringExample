@txn
Feature: Users can be created, retrieved, and deleted

  Scenario Outline: client makes call to POST /users with a first name, last name, and birthday
    When the client posts to /users with a first name: "<firstName>", last name: "<lastName>", and birthday : <birthday>
    Then the client receives status code of 200
    And the response has a firstName field matching: "<firstName>"

    Examples:
      | firstName | lastName | birthday |
      | Sean      | Franklin | 1991     |
      | Todd      | Frank    | 1989     |

  Scenario Outline: client makes call to GET /users with id 1
    Given a user "<firstName>" exists in the db
    When the client calls a GET to users
    Then the client receives status code of <status>
    And the response has a firstName field matching: "<firstName>"

    Examples:
      | firstName | status |
      | Brett     | 200    |
      | Todd      | 200    |
