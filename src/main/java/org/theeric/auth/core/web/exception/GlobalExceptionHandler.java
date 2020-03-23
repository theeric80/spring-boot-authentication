package org.theeric.auth.core.web.exception;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.theeric.auth.core.web.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        final String fields = ex.getBindingResult().getFieldErrors().stream() //
                .map(FieldError::getField) //
                .collect(Collectors.joining(", "));

        final ErrorResponse r = ErrorResponse.builder(status) //
                .message(String.format("Validation failed for [%s]", fields)) //
                .path(getRequestURI(request)) //
                .build();

        return new ResponseEntity<Object>(r, status);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleWebApplicationException( //
            WebApplicationException ex, WebRequest request) {

        final ErrorResponse r = ErrorResponse.builder(ex.getStatus()) //
                .code(ex.getCode()) //
                .message(Optional.ofNullable(ex.getReason()).orElse("")) //
                .path(getRequestURI(request)) //
                .build();

        return new ResponseEntity<ErrorResponse>(r, ex.getStatus());
    }

    private String getRequestURI(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI().toString();
    }

}
