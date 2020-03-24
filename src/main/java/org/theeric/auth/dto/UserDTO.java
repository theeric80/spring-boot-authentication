package org.theeric.auth.dto;

import org.theeric.auth.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UserDTO {

    private final Long id;

    private final String username;

    @JsonInclude(Include.NON_NULL)
    final private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = null;
    }

    public UserDTO(User user, String email) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
