package org.theeric.auth.user.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.core.service.AbstractService;
import org.theeric.auth.core.web.exception.ClientErrorException;
import org.theeric.auth.user.form.UserForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.model.UserRole;
import org.theeric.auth.user.repository.UserDao;

@Service
public class UserService extends AbstractService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public User create(User user) {
        final User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setFirstname(Optional.ofNullable(user.getFirstname()).orElse(""));
        u.setLastname(Optional.ofNullable(user.getLastname()).orElse(""));
        u.setEmail(Optional.ofNullable(user.getEmail()).orElse(""));
        u.setRole(UserRole.ROLE_USER);
        return userDao.save(u);
    }

    @Transactional
    public User update(Long id, UserForm form) {
        final User user = find(id) //
                .orElseThrow(() -> ClientErrorException.notFound("User not found"));

        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setEmail(form.getEmail());
        return user;
    }

    public Optional<User> find(Long id) {
        return userDao.findById(id);
    }

    public User findOrNotFound(Long id) {
        return find(id).orElseThrow(() -> ClientErrorException.notFound("User not found"));
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
