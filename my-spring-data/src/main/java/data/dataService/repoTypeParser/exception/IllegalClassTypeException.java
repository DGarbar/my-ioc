package data.dataService.repoTypeParser.exception;

public class IllegalClassTypeException extends RuntimeException {

	public IllegalClassTypeException() {
	}

	public IllegalClassTypeException(String message) {
		super(message);
	}

	public IllegalClassTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalClassTypeException(Throwable cause) {
		super(cause);
	}

	public IllegalClassTypeException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
