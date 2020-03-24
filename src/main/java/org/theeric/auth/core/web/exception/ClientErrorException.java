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

    static public ClientErrorException badRequest(String reason) {
        return new ClientErrorException(HttpStatus.BAD_REQUEST, reason);
    }

    static public ClientErrorException unauthorized(String reason) {
        return new ClientErrorException(HttpStatus.UNAUTHORIZED, reason);
    }

    static public ClientErrorException forbidden(String reason) {
        return new ClientErrorException(HttpStatus.FORBIDDEN, reason);
    }

    static public ClientErrorException notFound(String reason) {
        return new ClientErrorException(HttpStatus.NOT_FOUND, reason);
    }

    static public ClientErrorException conflict(String reason) {
        return new ClientErrorException(HttpStatus.CONFLICT, reason);
    }

    static public ClientErrorException tooManyRequests(String reason) {
        return new ClientErrorException(HttpStatus.TOO_MANY_REQUESTS, reason);
    }

}
