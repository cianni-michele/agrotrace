package it.unicam.cs.agrotrace.exception.handler;

import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.exception.ContentValidationException;
import it.unicam.cs.agrotrace.exception.UnauthorizedOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ErrorResponse handleUnauthorizedOperationException(UnauthorizedOperationException ex) {
        return ErrorResponse.builder(
                ex,
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        ).build();
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ErrorResponse handleContentNotFoundException(ContentNotFoundException ex) {
        return ErrorResponse.builder(
                ex,
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        ).build();
    }

    @ExceptionHandler(ContentValidationException.class)
    public ErrorResponse handleContentValidationException(ContentValidationException ex) {
        return ErrorResponse.builder(
                ex,
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        ).build();
    }
}
