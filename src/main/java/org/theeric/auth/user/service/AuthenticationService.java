package org.theeric.auth.user.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.core.web.exception.ClientErrorException;
import org.theeric.auth.dto.AuthToken;
import org.theeric.auth.user.form.LoginForm;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserSession;
import org.theeric.auth.user.repository.UserSessionDao;

@Service
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private UserSessionDao userSessionDao;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserSessionDao(UserSessionDao userSessionDao) {
        this.userSessionDao = userSessionDao;
    }

    @Transactional
    public User register(RegistrationForm form) {
        final Optional<User> user = userService.findByRef(form.getReference());
        if (user.isPresent()) {
            throw ClientErrorException.conflict("User already exists");
        } else {
            form.setPassword(hashpw(form.getPassword()));
            return userService.create(form);
        }
    }

    @Transactional
    public AuthToken login(LoginForm form) {
        final User user = userService.findByRef(form.getReference()) //
                .orElseThrow(() -> ClientErrorException.notFound("User not found"));

        if (!checkpw(form.getPassword(), user.getPassword())) {
            throw ClientErrorException.unauthorized("Incorrect username or password");
        }

        final UserSession session = createUserSession(user);
        return new AuthToken(session.getToken());
    }

    private UserSession createUserSession(User user) {
        final UserSession s = new UserSession();
        s.setToken(generateToken());
        s.setUser(user);
        return userSessionDao.save(s);
    }

    private String hashpw(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean checkpw(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateToken() {
        final SecureRandom random = new SecureRandom();
        final byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
