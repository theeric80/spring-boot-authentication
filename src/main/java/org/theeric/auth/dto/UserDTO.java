package org.theeric.auth.dto;

import org.theeric.auth.user.model.User;

public class UserDTO {

    private final Long id;

    private final String username;

    private final String firstname;

    private final String lastname;

    private final String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

}
