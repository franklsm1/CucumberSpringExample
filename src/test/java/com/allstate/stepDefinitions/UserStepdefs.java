package com.allstate.stepDefinitions;

import com.allstate.UserGroupsApplication;
import com.allstate.controller.UsersController;
import com.allstate.repository.UsersRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = UserGroupsApplication.class)
@WebMvcTest(controllers = UsersController.class)
@AutoConfigureMockMvc(secure = false)
public class UserStepdefs {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Autowired
    UsersRepository usersRepository;

    @After
    public void cleanUp() {
        usersRepository.deleteAll();
        latestResult = null;
    }

    MockHttpServletResponse latestResult;

    @When("^the client calls /users$")
    public void theClientCallsUsers() throws Throwable {
        MockHttpServletRequestBuilder request = get("/users");
        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        assertThat(latestResult.getStatus(), is(statusCode));
    }

    @And("^the client receives an empty array$")
    public void theClientReceivesAnEmptyArray() throws Throwable {
        String response = latestResult.getContentAsString();
        //JSONArray response = new JSONArray(res);
        assertThat(response, equalTo(""));
    }

    @When("^the client posts to /users$")
    public void theClientPostsToUsers() throws Throwable {
        JSONObject mockUser = new JSONObject();
        mockUser.put("firstName", "Kim");
        mockUser.put("lastName", "Un");

        JSONArray groups = new JSONArray();

        mockUser.put("groups", groups);
        mockUser.put("birthday", 1990);

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUser.toString());

        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();
    }

    @And("^the response has a valid firstName field$")
    public void theResponseHasAValidFirstNameField() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject response = new JSONObject(latestResult.getContentAsString());
        assertThat(response.get("firstName"), equalTo("Kim"));
    }
}
