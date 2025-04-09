package ua.edu.ukma.hibskyi.messenger.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final int status;
    private final String message;

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
