package org.theeric.auth.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.theeric.auth.user.model.User;

@Transactional(readOnly = true)
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
