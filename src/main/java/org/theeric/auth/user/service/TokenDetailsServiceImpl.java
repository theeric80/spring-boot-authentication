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
import org.theeric.auth.dto.UserContextDTO;
import org.theeric.auth.user.model.UserRole;
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
        final UserContextDTO ctx = userSessionDao.findUserContextByToken(credential) //
                .orElseThrow(() -> new BadCredentialsException("Invalid token"));

        return buildToken(ctx.getToken(), ctx.getUserId(), ctx.getUserRole());
    }

    private Token buildToken(String token, Long userId, UserRole role) {
        final GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        final List<GrantedAuthority> authorities = ImmutableList.of(authority);
        return new Token(token, userId.toString(), authorities);
    }

}
