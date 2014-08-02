package galaxy.exception;

public class GalacticException extends Exception {

	private static final long serialVersionUID = 2418883376026983015L;

	private String message = null;
	 
    public GalacticException(String message) {
        super(message);
        this.message = message;
    }
 
    public GalacticException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
