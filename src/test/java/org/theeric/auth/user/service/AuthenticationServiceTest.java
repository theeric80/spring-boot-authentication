package org.theeric.auth.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.theeric.auth.core.web.exception.ClientErrorException;
import org.theeric.auth.dto.AuthToken;
import org.theeric.auth.user.form.LoginForm;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserSession;
import org.theeric.auth.user.repository.UserSessionDao;

@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public AuthenticationService authenticationService() {
            return new AuthenticationService();
        }
    }

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @MockBean
    private UserSessionDao userSessionDao;

    @Autowired
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {}

    @Test
    public void whenRegisterOk_thenUserShouldBeCreated() {
        when(userService.create(any())).then(i -> i.getArgument(0));

        final RegistrationForm form = new RegistrationForm();
        form.setReference("test@gmail.com");
        form.setPassword("password");

        final User user = authenticationService.register(form);

        verify(userService, times(1)).create(any());

        assertThat(user.getReference()).isEqualTo(form.getReference());
    }

    @Test
    public void whenRegisterOk_thenPasswordShouldBeHashed() {
        final String expected = "hashed_password";

        when(passwordEncoder.encode("password")).thenReturn(expected);
        when(userService.create(any())).then(i -> i.getArgument(0));

        final RegistrationForm form = new RegistrationForm();
        form.setReference("test@gmail.com");
        form.setPassword("password");

        final User user = authenticationService.register(form);

        assertThat(user.getPassword()).isEqualTo(expected);
    }

    @Test
    public void whenUserExists_thenExceptionShouldBeThrown() {
        when(userService.findByRef(any())).thenReturn(Optional.of(new User()));

        final ClientErrorException e = Assertions.assertThrows(ClientErrorException.class, () -> {

            authenticationService.register(new RegistrationForm());
        });

        assertThat(e.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void whenLoginOk_thenTokenShouldBeReturned() {
        final String expected = "token";

        final User user = new User();
        user.setPassword("hashed_password");

        final UserSession session = new UserSession();
        session.setToken(expected);
        session.setUser(user);

        when(passwordEncoder.matches("password", "hashed_password")).thenReturn(true);
        when(userService.findByRef(any())).thenReturn(Optional.of(user));
        when(userSessionDao.save(any())).thenReturn(session);

        final LoginForm form = new LoginForm();
        form.setReference("test@gmail.com");
        form.setPassword("password");

        final AuthToken token = authenticationService.login(form);

        assertThat(token.getToken_type()).isEqualTo("Bearer");
        assertThat(token.getAccess_token()).isEqualTo(expected);
    }

    @Test
    public void whenUserNotExists_thenExceptionShouldBeThrown() {
        final ClientErrorException e = Assertions.assertThrows(ClientErrorException.class, () -> {

            authenticationService.login(new LoginForm());
        });

        assertThat(e.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenPasswordInvalid_thenExceptionShouldBeThrown() {
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        when(userService.findByRef(any())).thenReturn(Optional.of(new User()));

        final ClientErrorException e = Assertions.assertThrows(ClientErrorException.class, () -> {

            authenticationService.login(new LoginForm());
        });

        assertThat(e.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void whenlogoutOk_thenTokenShouldBeDeleted() {
        final String expected = "token";

        authenticationService.logout(expected);

        verify(userSessionDao, times(1)).deleteByToken(expected);
    }

}
