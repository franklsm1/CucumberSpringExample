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
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserGroupsApplication.class)
@WebMvcTest(controllers = UsersController.class)
@AutoConfigureMockMvc(secure = false)
public class UserStepdefs {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Autowired
    private UsersRepository usersRepository;

//    @Autowired
//    public void setUsersRepository(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }

    @After
    public void cleanUp() {
        //usersRepository.deleteAll();
        latestResult = null;
    }

    MockHttpServletResponse latestResult;

    @Transactional
    @When("^the client calls /users with id (\\d+)$")
    public void theClientCallsUsersWithId(long userID) throws Throwable {
        JSONObject mockUser = new JSONObject();
        mockUser.put("firstName", "Sean");
        mockUser.put("lastName", "Franklin");

        JSONArray groups = new JSONArray();

        mockUser.put("groups", groups);
        mockUser.put("birthday", 1991);

        MockHttpServletRequestBuilder request = get("/users/" + userID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUser.toString());
        latestResult = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        assertThat(latestResult.getStatus(), is(statusCode));
    }

    @Transactional
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
