package org.theeric.auth.core.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@SuppressWarnings("serial")
public class ServerErrorException extends WebApplicationException {

    public ServerErrorException(HttpStatus status) {
        super(status);
    }

    public ServerErrorException(HttpStatus status, @Nullable String reason) {
        super(status, reason);
    }

    public ServerErrorException(HttpStatus status, @Nullable String reason, int code) {
        super(status, reason, code);
    }

    public ServerErrorException(HttpStatus status, @Nullable String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public ServerErrorException( //
            HttpStatus status, @Nullable String reason, int code, Throwable cause) {
        super(status, reason, cause);
    }

}
