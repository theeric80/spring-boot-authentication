package org.theeric.auth.core.web.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource =
            new WebAuthenticationDetailsSource();

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    protected AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return authenticationDetailsSource;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final Authentication authResult = attemptAuthentication(request);

            successfulAuthentication(authResult);

        } catch (AuthenticationException failed) {
            unsuccessfulAuthentication(failed);
        }

        filterChain.doFilter(request, response);
    }

    protected Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
        final String token = obtainToken(request);

        final TokenAuthenticationToken authRequest = new TokenAuthenticationToken(token);
        setDetails(authRequest, request);

        return getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainToken(HttpServletRequest request) throws AuthenticationException {
        final String header = request.getHeader("Authorization");

        if (StringUtils.isBlank(header)) {
            throw new BadCredentialsException("");
        }

        return StringUtils.substring(header, 7);
    }

    protected void setDetails(AbstractAuthenticationToken authRequest, HttpServletRequest request) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    protected void successfulAuthentication(Authentication authResult) {
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    protected void unsuccessfulAuthentication(AuthenticationException failed) {
        SecurityContextHolder.clearContext();
    }

}
