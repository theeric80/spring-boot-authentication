package org.theeric.auth.user.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModelProperty;

public class LoginForm {

    @NotBlank
    @Size(max = 128)
    @ApiModelProperty(required = true)
    private String username;

    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(required = true)
    private String password;

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

}
