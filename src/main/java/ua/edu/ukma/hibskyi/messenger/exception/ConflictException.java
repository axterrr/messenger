package ua.edu.ukma.hibskyi.messenger.exception;

public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super(409, "Conflict: " + message);
    }
}
