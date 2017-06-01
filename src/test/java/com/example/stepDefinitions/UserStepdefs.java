package com.example.stepDefinitions;

import com.example.UserGroupsApplication;
import com.example.model.User;
import com.example.model.UserRequest;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = UserGroupsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserStepdefs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService usersService;

    private final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private MockHttpServletResponse latestResult;
    private JSONObject jsonResult;
    private JSONArray jsonArrayResult;
    private UserRequest user;
    private long newId;

    @Before
    public void cleanUp() throws JSONException {
        latestResult = null;
    }

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

    @And("^if the (\\d+) is (\\d+) the response has an \"([^\"]*)\" field$")
    public void ifTheStatusIsATheResponseHasAnIdField(int expectedStatus, int successStatus, String field) throws Throwable {
        if (expectedStatus == successStatus) {
            assertTrue(jsonResult.has(field));
        }
    }

    @Given("^a user exists in the db with the following info if valid:")
    public void aUserExistsInTheDb(List<UserRequest> users) throws Throwable {
        user = users.get(0);
        if (!"".equals(user.getFirstName())) {
            User newUser = usersService.save(user);
            newId = newUser.getId();
        } else {
            newId = -7;
        }
    }

    @When("^the client calls a GET to the \"([^\"]*)\" endpoint with a userId$")
    public void theClientCallsGetUsersWithId(String route) throws Throwable {
        MockHttpServletRequestBuilder request = get(route + "/" + newId)
                .contentType(MediaType.APPLICATION_JSON);

        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();

        if (newId > 0) {
            jsonResult = new JSONObject(latestResult.getContentAsString());
        }
    }

    @Given("^users exists in the db with the following info:")
    public void usersExistsInTheDb(List<UserRequest> users) throws Throwable {
        users.forEach(user -> usersService.save(user));
    }

    @When("^the client calls a GET to the \"([^\"]*)\" endpoint$")
    public void theClientCallsGetAllUsers(String route) throws Throwable {
        MockHttpServletRequestBuilder request = get(route)
                .contentType(MediaType.APPLICATION_JSON);

        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();

        jsonArrayResult = new JSONArray(latestResult.getContentAsString());
    }

    @And("^the response is an array with length (\\d+)$")
    public void theResponseIsAnArrayWithLength(int expectedSize) throws Throwable {
        assertEquals(expectedSize, jsonArrayResult.length());
    }
}
