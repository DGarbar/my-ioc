package ioc.Util.data.exception;

public class PersistenceXmlParserException extends RuntimeException {

    public PersistenceXmlParserException() {
    }

    public PersistenceXmlParserException(String message) {
        super(message);
    }

    public PersistenceXmlParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceXmlParserException(Throwable cause) {
        super(cause);
    }

    public PersistenceXmlParserException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
