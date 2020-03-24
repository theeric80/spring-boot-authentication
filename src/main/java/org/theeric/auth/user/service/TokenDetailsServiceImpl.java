package org.theeric.auth.user.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.core.web.authentication.Token;
import org.theeric.auth.core.web.authentication.TokenDetails;
import org.theeric.auth.core.web.authentication.TokenDetailsService;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserRole;
import org.theeric.auth.user.model.UserSession;
import org.theeric.auth.user.repository.UserSessionDao;
import com.google.common.collect.ImmutableList;

public class TokenDetailsServiceImpl implements TokenDetailsService {

    private UserSessionDao userSessionDao;

    @Autowired
    public void setUserSessionDao(UserSessionDao userSessionDao) {
        this.userSessionDao = userSessionDao;
    }

    @Transactional
    @Override
    public TokenDetails loadTokenByCredential(String credential) {
        final UserSession session = userSessionDao.findByToken(credential) //
                .orElseThrow(() -> new BadCredentialsException("Invalid token"));

        // FIXME: N+1 query
        final User user = session.getUser();
        return buildToken(session.getToken(), user.getId(), user.getRole());
    }

    private Token buildToken(String token, Long userId, UserRole role) {
        final GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        final List<GrantedAuthority> authorities = ImmutableList.of(authority);
        return new Token(token, userId.toString(), authorities);
    }

}
