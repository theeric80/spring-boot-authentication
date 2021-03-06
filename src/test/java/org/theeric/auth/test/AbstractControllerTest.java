package org.theeric.auth.test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.theeric.auth.core.web.authentication.TokenDetails;
import org.theeric.auth.core.web.authentication.TokenDetailsService;
import org.theeric.auth.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
public class AbstractControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private TokenDetailsService tokenDetailsService;

    protected final User AUTHENTICATED_USER = UserMother.newUser();

    protected final String ACCESS_TOKEN = "AbCdEf123456";

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders //
                .webAppContextSetup(wac) //
                .apply(springSecurity()) //
                .build();

        final TokenDetails token = UserMother.newTokenDetail(ACCESS_TOKEN, AUTHENTICATED_USER);
        when(tokenDetailsService.loadTokenByCredential(ACCESS_TOKEN)).thenReturn(token);
    }

    protected String json(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

}
