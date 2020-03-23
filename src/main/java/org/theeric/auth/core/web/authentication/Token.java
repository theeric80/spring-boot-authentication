package org.theeric.auth.core.web.authentication;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import com.google.common.collect.ImmutableSet;

public class Token implements TokenDetails, CredentialsContainer {

    private final String token;
    private final String subject;
    private final boolean active;
    private final boolean expired;
    private final Set<GrantedAuthority> authorities;

    public Token(String token, String subject, Collection<? extends GrantedAuthority> authorities) {
        this(token, subject, true, false, authorities);
    }

    public Token(String token, String subject, boolean active, boolean expired,
            Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.subject = subject;
        this.active = active;
        this.expired = expired;
        this.authorities = ImmutableSet.copyOf(authorities);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public void eraseCredentials() {}

}
