package ioc.exception;

public class NotClassException extends RuntimeException {

    public NotClassException() {
    }

    public NotClassException(String message) {
        super(message);
    }

    public NotClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotClassException(Throwable cause) {
        super(cause);
    }

    public NotClassException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
