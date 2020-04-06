package org.theeric.auth.user.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theeric.auth.core.context.UserContextHolder;
import org.theeric.auth.dto.UserDTO;
import org.theeric.auth.user.form.UserForm;
import org.theeric.auth.user.model.User;
import org.theeric.auth.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/me", //
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getMe() {
        final Long id = UserContextHolder.getContext().getUserId();
        final User user = userService.findOrNotFound(id);
        return new UserDTO(user);
    }

    @PutMapping(path = "/me", //
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO updateMe(@Valid @RequestBody UserForm form) {
        final Long id = UserContextHolder.getContext().getUserId();
        final User user = userService.update(id, form);
        return new UserDTO(user);
    }

}
