package ioc.exception;

public class NotEmptyConstructorProvidedException extends RuntimeException {

    public NotEmptyConstructorProvidedException() {
    }

    public NotEmptyConstructorProvidedException(String message) {
        super(message);
    }

    public NotEmptyConstructorProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEmptyConstructorProvidedException(Throwable cause) {
        super(cause);
    }

    public NotEmptyConstructorProvidedException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
