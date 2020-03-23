package org.theeric.auth.user.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.theeric.auth.core.web.exception.ClientErrorException;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.model.User;

@Service
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public User register(RegistrationForm form) {
        final Optional<User> user = userService.find(form.getReference());
        if (user.isPresent()) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "User already exists");
        } else {
            form.setPassword(hashpw(form.getPassword()));
            return userService.create(form);
        }
    }

    private String hashpw(String password) {
        return passwordEncoder.encode(password);
    }

}
