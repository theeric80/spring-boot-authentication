package org.theeric.auth.test;

import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.theeric.auth.core.web.authentication.Token;
import org.theeric.auth.core.web.authentication.TokenDetails;
import org.theeric.auth.dto.AuthToken;
import org.theeric.auth.user.form.LoginForm;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.form.UserForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserRole;
import org.theeric.auth.user.model.UserSession;
import com.google.common.collect.ImmutableList;

public class UserMother {

    public static final Long DEFAULT_USERID = 1L;
    public static final String DEFAULT_USERNAME = "eric_tsai";
    public static final String DEFAULT_USEREEMAIL = "eric_tsai@example.com";
    public static final String DEFAULT_PASSWORD = "0000";

    public static User newAdmin() {
        return newAdmin(DEFAULT_USERNAME);
    }

    public static User newAdmin(String username) {
        final User user = newUser(username);
        user.setRole(UserRole.ROLE_ADMIN);
        return user;
    }

    public static User newUser() {
        return newUser(DEFAULT_USERID, DEFAULT_USERNAME);
    }

    public static User newUser(Long id) {
        return newUser(id, DEFAULT_USERNAME);
    }

    public static User newUser(String username) {
        return newUser(DEFAULT_USERID, username);
    }

    public static User newUser(User user) {
        return newUser(user.getId(), user.getUsername());
    }

    public static User newUser(Long id, String username) {
        final User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(DEFAULT_PASSWORD);
        user.setEmail(DEFAULT_USEREEMAIL);
        user.setRole(UserRole.ROLE_USER);
        return user;
    }

    public static UserSession newUserSession(String token) {
        return newUserSession(newUser(), token, new Date(1577836800L));
    }

    public static UserSession newUserSession(User user, String token) {
        return newUserSession(user, token, new Date(1577836800L));
    }

    public static UserSession newUserSession(User user, String token, Date createdAt) {
        final UserSession session = new UserSession();
        session.setId(1L);
        session.setToken(token);
        session.setUser(user);
        session.setCreatedAt(createdAt);
        return session;
    }

    public static RegistrationForm newRegistrationForm() {
        return newRegistrationForm(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static RegistrationForm newRegistrationForm(String username, String password) {
        final RegistrationForm form = new RegistrationForm();
        form.setUsername(username);
        form.setPassword(password);
        return form;
    }

    public static LoginForm newLoginForm() {
        return newLoginForm(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static LoginForm newLoginForm(String username, String password) {
        final LoginForm form = new LoginForm();
        form.setUsername(username);
        form.setPassword(password);
        return form;
    }

    public static UserForm newUserForm() {
        final UserForm form = new UserForm();
        form.setFirstname("firstname_modified");
        form.setLastname("lastname_modified");
        form.setEmail(DEFAULT_USEREEMAIL);
        return form;
    }

    public static AuthToken newAuthToken(String accessToken) {
        return new AuthToken(accessToken);
    }

    public static TokenDetails newTokenDetail(String accessToken, User user) {
        final GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        final List<GrantedAuthority> authorities = ImmutableList.of(authority);
        return new Token(accessToken, user.getId().toString(), authorities);
    }

}
