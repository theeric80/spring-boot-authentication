package org.theeric.auth.core.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@SuppressWarnings("serial")
public class ClientErrorException extends WebApplicationException {

    public ClientErrorException(HttpStatus status) {
        super(status);
    }

    public ClientErrorException(HttpStatus status, @Nullable String reason) {
        super(status, reason);
    }

    public ClientErrorException(HttpStatus status, @Nullable String reason, int code) {
        super(status, reason, code);
    }

    public ClientErrorException(HttpStatus status, @Nullable String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public ClientErrorException( //
            HttpStatus status, @Nullable String reason, int code, Throwable cause) {
        super(status, reason, cause);
    }

}
