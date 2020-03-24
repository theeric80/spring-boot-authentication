package org.theeric.auth.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.dto.UserContextDTO;
import org.theeric.auth.user.model.UserSession;

@Transactional(readOnly = true)
public interface UserSessionDao extends JpaRepository<UserSession, Long> {

    @Query("SELECT new org.theeric.auth.dto.UserContextDTO(u.id, u.role, s.token) " + //
            " FROM UserSession s " + //
            " JOIN s.user u " + //
            "WHERE s.token = :token")
    Optional<UserContextDTO> findUserContextByToken(String token);

}
