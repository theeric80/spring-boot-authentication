package org.theeric.auth.test;

import org.theeric.auth.user.form.LoginForm;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserRole;
import org.theeric.auth.user.model.UserSession;

public class UserMother {

    public static final String DEFAULT_USERNAME = "eric_tsai";
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
        return newUser(DEFAULT_USERNAME);
    }

    public static User newUser(String username) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(DEFAULT_PASSWORD);
        user.setRole(UserRole.ROLE_USER);
        return user;
    }

    public static UserSession newUserSession(String token) {
        return newUserSession(newUser(), token);
    }

    public static UserSession newUserSession(User user, String token) {
        final UserSession session = new UserSession();
        session.setToken(token);
        session.setUser(user);
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

}
