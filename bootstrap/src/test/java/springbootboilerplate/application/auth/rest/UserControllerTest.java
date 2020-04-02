package springbootboilerplate.application.auth.rest;

import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springbootboilerplate.application.admin.usecase.AddUserCase;
import springbootboilerplate.application.admin.usecase.UpdateUserCase;
import springbootboilerplate.application.auth.APIBaseTest;
import springbootboilerplate.application.auth.JWTTokenStore;
import springbootboilerplate.application.auth.fixture.UserFixture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends APIBaseTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JWTTokenStore jwtTokenStore;

    @Autowired
    UserFixture userFixture;

    @Test
    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    public void should_create_user() throws Exception {
        AddUserCase.Request userAddRequest = AddUserCase.Request.of(
                "test1",
                "test.email@email1.com",
                "136681939013",
                true,
                10L
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userAddRequest));

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("test1")))
                .andExpect(jsonPath("$.email", is("test.email@email1.com")))
                .andExpect(jsonPath("$.phone", is("136681939013")))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.departmentId", is(10)));
    }

    @Test
    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    public void should_get_user_list() throws Exception {
        userFixture.createAdminUser();
        userFixture.createNormalUser();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.content[0].username", is("zhangsan")))
                .andExpect(jsonPath("$.content[0].email", is("zhangsan@email.com")))
                .andExpect(jsonPath("$.content[0].phone", is("13668193903")))
                .andExpect(jsonPath("$.content[0].enabled", is(true)));
    }

    @Test
    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    public void should_update_user() throws Exception {
        User normalUser = clone(userFixture.createNormalUser(), User.class);
        System.out.println(normalUser);

        UpdateUserCase.Request userAddRequest = UpdateUserCase.Request.of(
                "newTest1",
                "newtest.email@email1.com",
                "139681939013",
                false
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/users/{userId}", normalUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userAddRequest));

        this.mockMvc.perform(request).andExpect(status().isOk());
        User updatedUser = userFixture.userRepository.getOne(normalUser.getId());

        assertThat(updatedUser.getUsername(), is(userAddRequest.getUsername()));
        assertThat(updatedUser.getEmail(), is(userAddRequest.getEmail()));
        assertThat(updatedUser.getPhone(), is(userAddRequest.getPhone()));
        assertThat(updatedUser.getEnabled(), is(userAddRequest.getEnabled()));

        System.out.println(updatedUser);
        System.out.println(normalUser);
        assertThat(updatedUser.getUpdateAt(), not(normalUser.getUpdateAt()));
    }

    @Test
    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    public void should_delete_user() throws Exception {
        User normalUser = userFixture.createNormalUser();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/users/{userId}", normalUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andExpect(status().isOk());

        assertThat(userFixture.userRepository.existsById(normalUser.getId()), is(false));
    }

    @Test
    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    public void should_get_user() throws Exception {
        User normalUser = userFixture.createAdminUser();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/users/{userId}", normalUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("zhangsan")))
                .andExpect(jsonPath("$.email", is("zhangsan@email.com")))
                .andExpect(jsonPath("$.phone", is("13668193903")))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.departmentId", is(10)));
    }
}
