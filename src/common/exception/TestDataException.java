package common.exception;

/**
 * Manages exceptions triggered by invalid interactions with the GUI caused by malformed FieldData or FieldDataInstance metadata.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class TestDataException extends GUIException {
	private static final long serialVersionUID = 37L;
	
	public TestDataException() {
		super();
	}

	public TestDataException(String message) {
		super(message);
	}

	public TestDataException(Throwable cause) {
		super(cause);
	}
	
	public TestDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
