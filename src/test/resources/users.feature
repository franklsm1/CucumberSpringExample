@txn
Feature: Users creation and retrieval endpoint
  A user can be created if:
  * A firstName and lastName field exists
  * A birthYear field that is an 4 digit number exists


  Scenario Outline: client makes a POST request to the users endpoint
    Given a user with the following fields:
      | firstName   | lastName   | birthYear   |
      | <firstName> | <lastName> | <birthYear> |
    When a POST request to the "/users" endpoint is made with that user
    Then the response has a status of <status> and a message containing "<message>"
    And if the <status> is 201 the response has a "user" object with an "id" field

    Examples:
      | firstName | lastName | birthYear | status | message     |
      | Sean      | Franklin | 1991      | 201    | Created     |
      | Todd      | Frank    | 00000     | 400    | Bad Request |

  Scenario Outline: client makes a GET request to the users endpoint
    Given a user exists in the db with the following info:
      | firstName   | lastName   | birthYear   |
      | <firstName> | <lastName> | <birthYear> |
    When the client calls a GET to the "/users" endpoint
    Then the response has a status of <status> and a message containing "<message>"
    And if the <status> is 200 the response has a "user" object with an "id" field

    Examples:
      | firstName | lastName | birthYear | status | message |
      | Bret      | Palmer   | 1950      | 200    | OK      |
      | Todd      | Park     | 1975      | 200    | OK      |
