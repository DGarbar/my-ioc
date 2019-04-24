package ioc.exception;

public class MultipleBeanMatch extends RuntimeException {

    public MultipleBeanMatch() {
    }

    public MultipleBeanMatch(String message) {
        super(message);
    }

    public MultipleBeanMatch(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleBeanMatch(Throwable cause) {
        super(cause);
    }

    public MultipleBeanMatch(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
