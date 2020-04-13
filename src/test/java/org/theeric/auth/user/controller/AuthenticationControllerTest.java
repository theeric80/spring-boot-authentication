package org.theeric.auth.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.theeric.auth.dto.AuthToken;
import org.theeric.auth.test.AbstractControllerTest;
import org.theeric.auth.test.UserMother;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.service.AuthenticationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthenticationController.class)
@ActiveProfiles("test")
public class AuthenticationControllerTest extends AbstractControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void whenRegister_thenUserShouldBeCreated() throws Exception {
        final RegistrationForm form = UserMother.newRegistrationForm();

        mvc.perform(post("/api/auth/register") //
                .content(json(form)) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isCreated());

        final ArgumentCaptor<RegistrationForm> formCaptor = ArgumentCaptor.forClass(RegistrationForm.class);
        verify(authenticationService, times(1)).register(formCaptor.capture());
        assertThat(formCaptor.getValue()).isEqualToComparingFieldByField(form);
    }

    @Test
    public void whenLogin_thenTokenShouldBeReturned() throws Exception {
        final AuthToken expected = UserMother.newAuthToken(ACCESS_TOKEN);

        when(authenticationService.login(any())).thenReturn(expected);

        mvc.perform(post("/api/auth/login") //
                .content(json(UserMother.newLoginForm())) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isCreated()) //
                .andExpect(jsonPath("$.access_token", is(not(blankOrNullString())))) //
                .andExpect(jsonPath("$.token_type", is("Bearer"))) //
                .andExpect(content().json(json(expected)));
    }

    @Test
    public void whenLogout_thenTokenShouldBeDeleted() throws Exception {
        mvc.perform(post("/api/auth/logout") //
                .header("Authorization", "Bearer " + ACCESS_TOKEN) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isNoContent());

        verify(authenticationService, times(1)).logout(ACCESS_TOKEN);
    }

}
