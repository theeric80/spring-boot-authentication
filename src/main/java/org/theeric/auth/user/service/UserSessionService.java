package org.theeric.auth.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.core.service.AbstractService;
import org.theeric.auth.user.model.UserSession;
import org.theeric.auth.user.repository.UserSessionDao;

@Service
public class UserSessionService extends AbstractService {

    private UserSessionDao userSessionDao;

    @Autowired
    public void setUserSessionDao(UserSessionDao userSessionDao) {
        this.userSessionDao = userSessionDao;
    }

    public Page<UserSession> list(Long userId, Pageable pageable) {
        return userSessionDao.findAllByUserId(userId, pageable);
    }

    @Transactional
    public void delete(Long id) {
        userSessionDao.deleteById(id);
    }

}
