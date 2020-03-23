package org.theeric.auth.core.web.authentication;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class TokenAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object token = "";

    public TokenAuthenticationToken(Object token) {
        super(null);
        this.principal = null;
        this.token = token;
        setAuthenticated(false);
    }

    public TokenAuthenticationToken(Object principal, Object token,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.token = "";
    }

}
