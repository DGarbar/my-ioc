package ioc.exception;

public class NotAppropriateConstructorProvidedException extends RuntimeException {

    public NotAppropriateConstructorProvidedException() {
    }

    public NotAppropriateConstructorProvidedException(String message) {
        super(message);
    }

    public NotAppropriateConstructorProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAppropriateConstructorProvidedException(Throwable cause) {
        super(cause);
    }

    public NotAppropriateConstructorProvidedException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
