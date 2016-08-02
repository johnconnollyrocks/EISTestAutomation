package common.exception;

/**
 * Manages exceptions triggered by invalid interactions with the GUI.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class GUIException extends Exception {
	private static final long serialVersionUID = 37L;
	
	public GUIException() {
		super();
	}

	public GUIException(String message) {
		super(message);
	}

	public GUIException(Throwable cause) {
		super(cause);
	}
	
	public GUIException(String message, Throwable cause) {
		super(message, cause);
	}
}
