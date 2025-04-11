package ua.edu.ukma.hibskyi.messenger.exception;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(403, "Forbidden", "You have no permission for this action");
    }
}
