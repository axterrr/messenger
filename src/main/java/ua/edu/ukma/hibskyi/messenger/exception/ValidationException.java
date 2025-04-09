package ua.edu.ukma.hibskyi.messenger.exception;

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(400, "Validation error: " + message);
    }
}
