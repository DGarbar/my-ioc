package data.dataService.dataBaseConnection.exception;

public class SqlMethodNameParseException extends RuntimeException {

	public SqlMethodNameParseException() {
	}

	public SqlMethodNameParseException(String message) {
		super(message);
	}

	public SqlMethodNameParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SqlMethodNameParseException(Throwable cause) {
		super(cause);
	}

	public SqlMethodNameParseException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
