package com.allstate.stepDefinitions;

import com.allstate.UserGroupsApplication;
import com.allstate.model.User;
import com.allstate.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.Transpose;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

import static java.util.Objects.isNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = UserGroupsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserStepdefs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    private final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private MockHttpServletResponse latestResult;
    private JSONObject jsonResult;
    private User user;

    @Before
    public void cleanUp() throws JSONException {
        latestResult = null;
    }

    @Given("^a user with the following fields:$")
    public void aUserWithTheFollowingFields(List<User> users) {
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

    @Then("^the response has a status of (\\d+) and a message containing \"([^\"]*)\"$")
    public void theResponseHasAStatusOfStatusAndAMessageContaining(int status, String message) throws Throwable {
        assertThat(latestResult.getStatus(), is(status));
        assertThat(jsonResult.get("message"), is(message));
    }

    @And("^if the (\\d+) is (\\d+) the response has a \"([^\"]*)\" object with an \"([^\"]*)\" field$")
    public void ifTheStatusIsATheResponseHasAnIdField(int expectedStatus, int successStatus, String object, String field) throws Throwable {
        if (expectedStatus == successStatus) {
            assertTrue(jsonResult.has(object));
            JSONObject user = (JSONObject) jsonResult.get(object);
            assertTrue(user.has(field));
        }
    }

    @Given("^a user exists in the db with the following info:")
    public void aUserExistsInTheDb(List<User> users) throws Throwable {
        user = users.get(0);
        usersRepository.save(user);
    }

    @When("^the client calls a GET to the \"([^\"]*)\" endpoint$")
    public void theClientCallsUsersWithId(String route) throws Throwable {
        MockHttpServletRequestBuilder request = get(route + "/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON);

        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();

        jsonResult = new JSONObject(latestResult.getContentAsString());
    }
}
