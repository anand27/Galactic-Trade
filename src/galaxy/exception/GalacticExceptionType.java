package galaxy.exception;

public enum GalacticExceptionType {

	SUBTRACTION_VIOLATION(101, "Subtraction violation - \"V\", \"L\", and \"D\" can never be subtracted"),
	
	SUBTRACTION_SET_INVALID_VIOLATION(102, "Subtraction violation - \"I\" can be subtracted from \"V\" and \"X\" only. \"X\" can be subtracted from \"L\" and \"C\" only. \"C\" can be subtracted from \"D\" and \"M\" only"),
	
	REPETITION_OCCURENCE_VIOLATION(103, "Repition violation - The symbols \"I\", \"X\", \"C\", and \"M\" can be repeated three times in succession, but no more"),
	
	REPETITION_VIOLATION(104, "Repition violation -  \"D\", \"L\", and \"V\" can never be repeated"),
	
	INVALID_ROMAN_NUMERAL_VIOLATION(105, "Invalid numeral violation - not a valid Roman numeral"),
	
	INVALID_GALACTIC_NUMERAL_VIOLATION(106, "Invalid numeral violation - not a valid Galactic numeral"),
	
	INVALID_METAL(107, "Invalid metal - metal does not exist");
	
	private int errorCode;
	private String message;
	
	private GalacticExceptionType(int code, String message) {
		this.errorCode = code;
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
	
}
