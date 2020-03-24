package springbootboilerplate.modules.auth.rest;

import cn.printf.springbootboilerplate.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springbootboilerplate.modules.auth.APIBaseTest;
import springbootboilerplate.modules.auth.AuthUser;
import springbootboilerplate.modules.auth.config.JWTTokenStore;
import springbootboilerplate.repository.UserRepository;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static springbootboilerplate.modules.auth.fixture.UserFixture.prepareAdminUser;

public class AuthenticationControllerTest extends APIBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JWTTokenStore jwtTokenStore;

    @Test
    public void should_get_validated_token() throws Exception {
        User user = userRepository.save(prepareAdminUser());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/v1/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(
                        ImmutableMap.of(
                                "username", user.getUsername(),
                                "password", user.getPassword()
                        )
                ));

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value", isA(String.class)));
    }

    @Test
    public void get_user_info() throws Exception {
        User user = userRepository.save(prepareAdminUser());
        String token = jwtTokenStore.generateToken(AuthUser.from(user));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/token/info")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", isA(String.class)));
    }
}
