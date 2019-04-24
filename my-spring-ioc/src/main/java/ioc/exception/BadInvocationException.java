package ioc.exception;

public class BadInvocationException extends RuntimeException {

    public BadInvocationException() {
    }

    public BadInvocationException(String message) {
        super(message);
    }

    public BadInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadInvocationException(Throwable cause) {
        super(cause);
    }

    public BadInvocationException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
