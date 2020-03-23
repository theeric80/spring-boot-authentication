package org.theeric.auth.user.service;

import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.theeric.auth.core.web.authentication.Token;
import org.theeric.auth.core.web.authentication.TokenDetails;
import org.theeric.auth.core.web.authentication.TokenDetailsService;
import org.theeric.auth.user.model.UserRole;
import com.google.common.collect.ImmutableList;

public class TokenDetailsServiceImpl implements TokenDetailsService {

    @Override
    public TokenDetails loadTokenByCredential(String credential) {
        throw new BadCredentialsException("");
    }

    @SuppressWarnings("unused")
    private Token buildToken(String token, Long userId, UserRole role) {
        final GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        final List<GrantedAuthority> authorities = ImmutableList.of(authority);
        return new Token(token, userId.toString(), authorities);
    }

}
