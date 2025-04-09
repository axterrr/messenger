package ua.edu.ukma.hibskyi.messenger.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(404, "Resource not found: " + message);
    }
}
