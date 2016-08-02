package common.exception;

/**
 * Manages exceptions triggered by invalid interactions with the GUI caused by malformed Window, Page, or Field metadata.
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public class MetadataException extends GUIException {
	private static final long serialVersionUID = 37L;
	
	public MetadataException() {
		super();
	}

	public MetadataException(String message) {
		super(message);
	}

	public MetadataException(Throwable cause) {
		super(cause);
	}
	
	public MetadataException(String message, Throwable cause) {
		super(message, cause);
	}
}
