package org.theeric.auth.dto;

import org.theeric.auth.user.model.UserRole;

public class UserContextDTO {

    private final Long userId;

    private final UserRole userRole;

    private final String token;

    public UserContextDTO(Long userId, UserRole role, String token) {
        this.userId = userId;
        this.userRole = role;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getToken() {
        return token;
    }

}
