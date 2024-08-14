package dev.pichborith.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException(Throwable cause) {
        super(cause);
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
