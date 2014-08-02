package galaxy.exception;

public class IllegalConversionException extends Exception {

	private static final long serialVersionUID = 5788330753672904660L;
	
	private String message = null;
	 
    public IllegalConversionException(String message) {
        super(message);
        this.message = message;
    }
 
    public IllegalConversionException(Throwable cause) {
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
