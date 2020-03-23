package org.theeric.auth.core.web.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenDetailsService tokenDetailsService;

    public TokenAuthenticationProvider(TokenDetailsService tokenDetailsService) {
        this.tokenDetailsService = tokenDetailsService;
    }

    public void setTokenDetailsService(TokenDetailsService tokenDetailsService) {
        this.tokenDetailsService = tokenDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String credential = authentication.getCredentials().toString();

        if (StringUtils.isBlank(credential)) {
            throw new BadCredentialsException("");
        }

        final TokenDetails token = tokenDetailsService.loadTokenByCredential(credential);

        if (!token.isActive() || token.isExpired()) {
            throw new CredentialsExpiredException("");
        }

        return createSuccessAuthentication(authentication, token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (TokenAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private Authentication createSuccessAuthentication(Authentication authentication, TokenDetails token) {
        final Object principal = token;
        final TokenAuthenticationToken result = new TokenAuthenticationToken( //
                principal, token.getToken(), token.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;
    }

}
