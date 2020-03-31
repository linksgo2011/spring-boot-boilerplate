package springbootboilerplate.application.auth.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springbootboilerplate.application.admin.usecase.UserAddCase;
import springbootboilerplate.application.auth.APIBaseTest;
import springbootboilerplate.application.auth.JWTTokenStore;
import springbootboilerplate.application.auth.fixture.UserFixture;

import static org.hamcrest.CoreMatchers.is;
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
        UserAddCase.Request userAddRequest = UserAddCase.Request.of(
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
}
