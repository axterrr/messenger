package ua.edu.ukma.hibskyi.messenger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ua.edu.ukma.hibskyi.messenger.dto.response.ErrorResponse;
import ua.edu.ukma.hibskyi.messenger.exception.BaseException;

@ControllerAdvice(basePackages = "ua.edu.ukma.hibskyi.messenger.controller.rest")
public class RestControllerExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getStatus(), ex.getName(), ex.getMessages()), HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(404, "Resource not found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(404, "Unexpected exception", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
