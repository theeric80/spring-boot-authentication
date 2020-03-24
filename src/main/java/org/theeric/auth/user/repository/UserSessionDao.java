package org.theeric.auth.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.user.model.UserSession;

@Transactional(readOnly = true)
public interface UserSessionDao extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByToken(String token);

}
