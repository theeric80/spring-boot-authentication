package org.theeric.auth.user.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.theeric.auth.test.AbstractControllerTest;
import org.theeric.auth.test.UserMother;
import org.theeric.auth.user.form.UserForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserControllerTest extends AbstractControllerTest {

    @MockBean
    private UserService userService;

    @Test
    public void whenGetMe_thenUserShouldBeReturned() throws Exception {
        final User expected = UserMother.newUser(AUTHENTICATED_USER);

        when(userService.findOrNotFound(AUTHENTICATED_USER.getId())).thenReturn(expected);

        mvc.perform(get("/api/users/me") //
                .header("Authorization", "Bearer " + ACCESS_TOKEN) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.id", is(expected.getId().intValue()))) //
                .andExpect(jsonPath("$.firstname", is(expected.getFirstname()))) //
                .andExpect(jsonPath("$.lastname", is(expected.getLastname()))) //
                .andExpect(jsonPath("$.email", is(expected.getEmail())));
    }

    @Test
    public void whenUpdateMe_thenUserShouldBeUpdated() throws Exception {
        final UserForm form = UserMother.newUserForm();
        final User expected = UserMother.newUser(AUTHENTICATED_USER);
        expected.setFirstname(form.getFirstname());
        expected.setLastname(form.getLastname());
        expected.setEmail(form.getEmail());

        when(userService.update(eq(AUTHENTICATED_USER.getId()), any())).thenReturn(expected);

        mvc.perform(put("/api/users/me") //
                .header("Authorization", "Bearer " + ACCESS_TOKEN) //
                .content(json(form)) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.id", is(expected.getId().intValue()))) //
                .andExpect(jsonPath("$.firstname", is(expected.getFirstname()))) //
                .andExpect(jsonPath("$.lastname", is(expected.getLastname()))) //
                .andExpect(jsonPath("$.email", is(expected.getEmail())));
    }

}
