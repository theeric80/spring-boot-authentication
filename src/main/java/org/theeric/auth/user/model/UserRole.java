package org.theeric.auth.user.model;

import org.apache.commons.lang3.StringUtils;

public enum UserRole {
    ROLE_ADMIN(), ROLE_USER();

    private final String role;

    private UserRole() {
        this.role = StringUtils.split(this.name(), "_")[1];
    }

    public String role() {
        return role;
    }

}
