# BDD Cucumber Java Spring Example
Java spring application using Cucumber for acceptance tests and a MySQL db for storing data

### Setup MySQL
1) install MySQL (on a mac) with: `brew install mysql`
2) start MySQL with: `mysql.server start`
3) access MySQL with: `mysql -u root`
4) inside of MySQL run the following two commands to create the databases that this example uses:
- `create database user_groups;`
- `create database user_groups_test;`
5) running the tests or starting the app will create the table structure for you, thank-you hibernate!

### Gradle Commands:
- Clean, build, run tests, and check code coverage: `./gradlew clean build`
- Start the application on *http://localhost:7777*: `./gradlew bootrun`

**Warning:** Need to use gradle v2.12 and up to build this project successfully

### Swagger Page:
- View the swagger UI when the app is running go to: *http://localhost:7777/swagger-ui.html*

### Testing in IntelliJ
1) Install `Lombok` and `Cucumber for Java` Intellij plugins through the preferences
2) Restart Intellij
3) Open the `src/test/resources` directory and right click on `users.feature` file then click run

### Key benefits of Cucumber for back-end applications:

- ability to wrap a transaction around each scenario by adding the "@txn" keyword at the top of a feature file
- mapping of a row in a Cucumber table to a request POJO for simplifying step definition function signatures
- code reuse by using data tables to allow the same scenario to be re-run with different inputs such as HTTP response codes and request objects


### Walkthrough of using Cucumber for testing a backend API

**Given** the following Cucumber scenario that either a developer or project manager can create:

```Cucumber
Scenario Outline: client makes a POST request to the users endpoint

    Given a user with the following fields:
      | firstName   | lastName   | birthYear   |
      | <firstName> | <lastName> | <birthYear> |
    When a POST request to the "/users" endpoint is made with that user
    Then the response has a status of <status>
    And if <status> equals 201 the response has an "id" field

    Examples:
      | firstName | lastName | birthYear | status |
      | Sean      | Franklin | 1991      | 201    |
      | Todd      | Frank    | 00000     | 400    |
```      
**When** using IntelliJ Community edition IDE, the above scenario can be used to auto generate step definitions like so:

```java
@Given("^a user with the following fields:$")
public void aUserWithTheFollowingFields() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@When("^a POST request to the \"([^\"]*)\" endpoint is made with that user$")
public void aPOSTRequestToTheEndpointIsMadeWithThatUser(String arg0) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Then("^the response has a status of (\\d+)$")
public void theResponseHasAStatusOf(int arg0) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@And("^if (\\d+) equals (\\d+) the response has an \"([^\"]*)\" field$")
public void ifStatusEqualsTheResponseHasAnField(int arg0, int arg1, String arg2) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}
```
**And** after the step definitions have been implemented similar to the following:

```java
@Given("^a user with the following fields:$")
public void aUserWithTheFollowingFields(List<UserRequest> users) {
    this.user = users.get(0);
}

@When("^a POST request to the \"([^\"]*)\" endpoint is made with that user$")
public void aPOSTRequestToTheUsersEndpointIsMade(String route) throws Throwable {
    String userJson = OBJ_MAPPER.writeValueAsString(this.user);

    MockHttpServletRequestBuilder request = post(route)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson);

    latestResult = this.mockMvc.perform(request)
            .andReturn()
            .getResponse();

    jsonResult = new JSONObject(latestResult.getContentAsString());
}

@Then("^the response has a status of (\\d+)$")
public void theResponseHasAStatusOfStatus(int status) throws Throwable {
    assertThat(latestResult.getStatus(), is(status));
}

@And("^if (\\d+) equals (\\d+) the response has an \"([^\"]*)\" field$")
public void ifTheStatusIsATheResponseHasAnIdField(int expectedStatus, int successStatus, String field) throws Throwable {
    if (expectedStatus == successStatus) {
        assertTrue(jsonResult.has(field));
    }
}
```

**Then** running the tests inside of IntelliJ will produce an output similar to:

![Image of cucumber output](https://github.com/franklsm1/UserGroupsAPI/blob/master/public/CucumberOutput.png)
