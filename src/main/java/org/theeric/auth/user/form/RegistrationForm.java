package org.theeric.auth.user.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModelProperty;

public class RegistrationForm {

    @NotBlank
    @Size(max = 128)
    @ApiModelProperty(required = true)
    private String username;

    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(required = true)
    private String password;

    @Size(max = 128)
    private String firstname = "";

    @Size(max = 128)
    private String lastname = "";

    @Size(max = 256)
    private String email = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
