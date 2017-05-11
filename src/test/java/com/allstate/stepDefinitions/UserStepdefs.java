package com.allstate.stepDefinitions;

import com.allstate.UserGroupsApplication;
import com.allstate.controller.UsersController;
import com.allstate.model.User;
import com.allstate.repository.UsersRepository;
import cucumber.api.PendingException;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = UserGroupsApplication.class)
//@WebMvcTest(controllers = UsersController.class)
//@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class UserStepdefs {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;


    private MockHttpServletResponse latestResult;

    private User user;

    @Before
    public void cleanUp() throws JSONException {
        latestResult = null;
    }
    @Given("^a user \"([^\"]*)\" exists in the db$")
    public void aUserExistsInTheDb(String name) throws Throwable {
        user = usersRepository.save(new User(name, "Franklin", 1991));
    }

    @When("^the client calls a GET to users$")
    public void theClientCallsUsersWithId() throws Throwable {

        MockHttpServletRequestBuilder request = get("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON);

        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        assertThat(latestResult.getStatus(), is(statusCode));
    }

    @When("^the client posts to /users with a first name: \"([^\"]*)\", last name: \"([^\"]*)\", and birthday : (\\d+)$")
    public void theClientPostsToUsersWithAAndBirthday(String firstName, String lastName, int birthday) throws Throwable {
        JSONObject mockUser = new JSONObject();
        mockUser.put("firstName", firstName);
        mockUser.put("lastName", lastName);

        JSONArray groups = new JSONArray();

        mockUser.put("groups", groups);
        mockUser.put("birthday", birthday);

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUser.toString());

        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();
    }

    @And("^the response has a firstName field matching: \"([^\"]*)\"$")
    public void theResponseHasAValidFirstNameField(String firstName) throws Throwable {
        JSONObject response = new JSONObject(latestResult.getContentAsString());
        assertThat(response.get("firstName").toString(), equalTo(firstName));
    }
}
