package org.theeric.auth.user.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModelProperty;

public class UserForm {

    @NotNull
    @Size(max = 128)
    @ApiModelProperty(required = true)
    private String firstname = "";

    @NotNull
    @Size(max = 128)
    @ApiModelProperty(required = true)
    private String lastname = "";

    @NotNull
    @Size(max = 256)
    @ApiModelProperty(required = true)
    private String email = "";

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
