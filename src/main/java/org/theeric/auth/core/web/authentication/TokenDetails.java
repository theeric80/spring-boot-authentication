package org.theeric.auth.core.web.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface TokenDetails {

    Collection<? extends GrantedAuthority> getAuthorities();

    String getToken();

    String getSubject();

    boolean isActive();

    boolean isExpired();

}
