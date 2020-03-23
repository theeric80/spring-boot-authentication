package org.theeric.auth.core.web;

import java.util.Date;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorResponse {

    private final Date timestamp;

    private final int status;

    private final String error;

    private final Integer code;

    private final String message;

    private final String path;

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    @JsonInclude(Include.NON_NULL)
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public static Builder builder(HttpStatus status) {
        return new Builder(status);
    }

    public static class Builder {
        // required
        private final HttpStatus status;

        // optional
        private Date timestamp = new Date();
        private Integer code = null;
        private String message = "";
        private String path = "";

        public Builder(HttpStatus status) {
            this.status = status;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }

    }

    public ErrorResponse(Builder builder) {
        this.timestamp = builder.timestamp;
        this.status = builder.status.value();
        this.error = builder.status.getReasonPhrase();
        this.code = builder.code;
        this.message = builder.message;
        this.path = builder.path;
    }

}
