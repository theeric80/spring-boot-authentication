package org.theeric.auth.user.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.theeric.auth.core.web.exception.ClientErrorException;
import org.theeric.auth.user.form.RegistrationForm;
import org.theeric.auth.user.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "/register", //
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegistrationForm form) {
        authenticationService.register(form);
    }

    public void login() {
        throw new ClientErrorException(HttpStatus.BAD_REQUEST);
    }

    public void logout() {}

}
