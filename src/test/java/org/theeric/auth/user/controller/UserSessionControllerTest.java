package org.theeric.auth.user.controller;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.theeric.auth.test.AbstractControllerTest;
import org.theeric.auth.test.PageMother;
import org.theeric.auth.test.UserMother;
import org.theeric.auth.user.model.UserSession;
import org.theeric.auth.user.service.UserSessionService;
import com.google.common.collect.ImmutableList;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserSessionController.class)
@ActiveProfiles("test")
public class UserSessionControllerTest extends AbstractControllerTest {

    @MockBean
    private UserSessionService userSessionService;

    @Test
    public void whenList_thenSessionsShouldBeReturned() throws Exception {
        final List<UserSession> expected = ImmutableList.of( //
                UserMother.newUserSession(AUTHENTICATED_USER, "token_1"), //
                UserMother.newUserSession(AUTHENTICATED_USER, "token_2") //
        );

        when(userSessionService.list(any(), any())).thenReturn(PageMother.newPage(expected));

        mvc.perform(get("/api/users/sessions/") //
                .header("Authorization", "Bearer " + ACCESS_TOKEN) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$", hasSize(expected.size()))) //
                .andExpect(jsonPath("$[0].id", is(expected.get(0).getId().intValue()))) //
                .andExpect(jsonPath("$[0].token", is(expected.get(0).getToken()))) //
                .andExpect(jsonPath("$[0].createdAt", is(not(blankOrNullString()))));
    }

    @Test
    public void whenDelete_thenSessionShouldBeDeleted() throws Exception {
        final Long sessionId = 1L;

        mvc.perform(delete("/api/users/sessions/" + sessionId) //
                .header("Authorization", "Bearer " + ACCESS_TOKEN) //
                .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isNoContent());

        verify(userSessionService, times(1)).delete(sessionId);
    }

}
