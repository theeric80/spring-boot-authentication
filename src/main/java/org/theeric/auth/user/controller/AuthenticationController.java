package org.theeric.auth.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theeric.auth.core.web.exception.ClientErrorException;
import org.theeric.auth.dto.AuthToken;
import org.theeric.auth.user.form.RegistrationForm;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @PostMapping(path = "/register", //
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthToken register(@RequestBody RegistrationForm form) {
        final String token = "access_token";
        return new AuthToken(token);
    }

    public void login() {
        throw new ClientErrorException(HttpStatus.BAD_REQUEST);
    }

    public void logout() {}

}
