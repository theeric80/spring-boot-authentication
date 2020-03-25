package org.theeric.auth.user.controller;

import java.util.List;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.theeric.auth.core.context.UserContextHolder;
import org.theeric.auth.dto.UserSessionDTO;
import org.theeric.auth.user.service.UserSessionService;

@RestController
@RequestMapping("/api/users/sessions")
public class UserSessionController {

    @Autowired
    private UserSessionService userSessionService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Sort.class, new SortEditor());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserSessionDTO> list( //
            @RequestParam(name = "page", required = false, defaultValue = "0") @Min(0) Integer page, //
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") Sort sort) {
        // TODO: next/prev/first/last
        final long userId = UserContextHolder.getContext().getUserId();
        final Pageable pageable = PageRequest.of(page, size, sort);
        return userSessionService.list(userId, pageable) //
                .map((o) -> new UserSessionDTO(o)) //
                .getContent();
    }

    @DeleteMapping(path = "/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) Long sessionId) {
        userSessionService.delete(sessionId);
    }

}
