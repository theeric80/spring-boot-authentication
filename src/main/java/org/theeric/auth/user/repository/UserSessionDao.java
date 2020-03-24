package org.theeric.auth.user.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.dto.UserContextDTO;
import org.theeric.auth.user.model.UserSession;

@Transactional(readOnly = true)
public interface UserSessionDao extends JpaRepository<UserSession, Long> {

    Page<UserSession> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT new org.theeric.auth.dto.UserContextDTO(u.id, u.role, s.token) " + //
            " FROM UserSession s " + //
            " JOIN s.user u " + //
            "WHERE s.token = :token")
    Optional<UserContextDTO> findUserContextByToken(String token);

    @EntityGraph(value = "session.detail")
    @Query("SELECT s FROM UserSession s WHERE s.token = :token")
    Optional<UserSession> findDetailByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserSession s WHERE s.token = :token")
    void deleteByToken(String token);

}
