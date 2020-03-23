package org.theeric.auth.user.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank
    @Size(max = 256)
    private String reference;

    @NotBlank
    @Size(max = 20)
    private String password;

    @Size(max = 128)
    private String username;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
