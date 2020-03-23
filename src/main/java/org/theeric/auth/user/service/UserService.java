package org.theeric.auth.user.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserRole;
import org.theeric.auth.user.repository.UserDao;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User create(RegistrationForm form) {
        final User u = new User();
        u.setReference(form.getReference());
        u.setPassword(form.getPassword());
        u.setUsername(Optional.ofNullable(form.getUsername()).orElse(""));
        u.setRole(UserRole.ROLE_USER);
        return userDao.save(u);
    }

    public Optional<User> find(String reference) {
        return userDao.findByReference(reference);
    }

}
