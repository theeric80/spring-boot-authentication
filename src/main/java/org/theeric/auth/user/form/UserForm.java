package org.theeric.auth.user.form;

import javax.validation.constraints.Size;

public class UserForm {

    @Size(max = 128)
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
