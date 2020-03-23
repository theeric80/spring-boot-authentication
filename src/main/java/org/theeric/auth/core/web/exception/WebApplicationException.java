package org.theeric.auth.core.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class WebApplicationException extends ResponseStatusException {

    private final Integer code;

    public WebApplicationException(HttpStatus status) {
        super(status, null);
        this.code = null;
    }

    public WebApplicationException(HttpStatus status, @Nullable String reason) {
        super(status, reason);
        this.code = null;
    }

    public WebApplicationException(HttpStatus status, @Nullable String reason, Throwable cause) {
        super(status, reason, cause);
        this.code = null;
    }

    public WebApplicationException(HttpStatus status, @Nullable String reason, int code) {
        super(status, reason);
        this.code = code;
    }

    public WebApplicationException( //
            HttpStatus status, @Nullable String reason, int code, Throwable cause) {
        super(status, reason, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
