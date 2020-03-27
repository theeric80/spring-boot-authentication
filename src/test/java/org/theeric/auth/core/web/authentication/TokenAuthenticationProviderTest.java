package org.theeric.auth.core.web.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.theeric.auth.user.model.UserRole;
import com.google.common.collect.ImmutableList;

@ExtendWith(SpringExtension.class)
public class TokenAuthenticationProviderTest {

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public AuthenticationProvider authenticationProvider() {
            return new TokenAuthenticationProvider(null);
        }
    }

    @MockBean
    private TokenDetailsService tokenDetailsService;

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @BeforeEach
    public void setUp() {
        tokenAuthenticationProvider.setTokenDetailsService(tokenDetailsService);
    }

    private final String token = "credential";

    @Test
    public void whenValidToken_thenAuthenticationShouldBeAuthenticated() {
        when(tokenDetailsService.loadTokenByCredential(token)).thenReturn(buildToken(token, true, false));

        final Authentication authRequest = new TokenAuthenticationToken(token);
        final Authentication authResult = tokenAuthenticationProvider.authenticate(authRequest);

        assertThat(authResult.isAuthenticated()).isTrue();
        assertThat(authResult.getPrincipal()).isInstanceOf(TokenDetails.class);
    }

    @Test
    public void whenNotfoundToken_thenBadCredentialsExceptionShouldBeThrown() {
        when(tokenDetailsService.loadTokenByCredential(token)).thenThrow(BadCredentialsException.class);

        Assertions.assertThrows(BadCredentialsException.class, () -> {

            final Authentication authRequest = new TokenAuthenticationToken(token);
            tokenAuthenticationProvider.authenticate(authRequest);
        });
    }

    @Test
    public void whenInactiveToken_thenCredentialsExpiredExceptionShouldBeThrown() {
        when(tokenDetailsService.loadTokenByCredential(token)).thenReturn(buildToken(token, false, false));

        Assertions.assertThrows(CredentialsExpiredException.class, () -> {

            final Authentication authRequest = new TokenAuthenticationToken(token);
            tokenAuthenticationProvider.authenticate(authRequest);
        });
    }

    @Test
    public void whenExpiredToken_thenCredentialsExpiredExceptionShouldBeThrown() {
        when(tokenDetailsService.loadTokenByCredential(token)).thenReturn(buildToken(token, true, true));

        Assertions.assertThrows(CredentialsExpiredException.class, () -> {

            final Authentication authRequest = new TokenAuthenticationToken(token);
            tokenAuthenticationProvider.authenticate(authRequest);
        });
    }

    private Token buildToken(String token, boolean active, boolean expired) {
        final String subject = "1";
        final GrantedAuthority authority = new SimpleGrantedAuthority(UserRole.ROLE_USER.name());
        final List<GrantedAuthority> authorities = ImmutableList.of(authority);
        return new Token(token, subject, active, expired, authorities);
    }

}
