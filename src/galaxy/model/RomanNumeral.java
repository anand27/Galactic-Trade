package galaxy.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum RomanNumeral {
	 
	V("V", 5), 
	L("L", 50), 
	D("D", 500), 
	M("M", 1000, true, true),
	C("C", 100, true, true, new ArrayList<RomanNumeral>(Arrays.asList(D, M))),
	X("X", 10, true, true, new ArrayList<RomanNumeral>(Arrays.asList(L, C))),
	I("I", 1, true, true, new ArrayList<RomanNumeral>(Arrays.asList(V, X)));
	
	private String type;
	private int value;
	private boolean isRepeatable;
	private boolean isSubtractable;
	private List<RomanNumeral> subtractionSet;
	
	public static final int MAX_OCCURENCE_IN_SUCCESSION = 3;
	
	public boolean isRepeatable() {
		return isRepeatable;
	}

	public boolean isSubtractable() {
		return isSubtractable;
	}

	public String getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	public List<RomanNumeral> getSubtractionSet() {
		return subtractionSet;
	}

	private RomanNumeral(String type, int val) {
		this(type, val, false, false);
	}
	
	private RomanNumeral(String type, int val, boolean repeatable, boolean subtractable) {
		this(type, val, repeatable, subtractable, null);
	}
	
	private RomanNumeral(String type, int val, boolean repeatable, boolean subtractable, List<RomanNumeral> set) {
		this.type = type;
		this.value = val;
		this.isRepeatable = repeatable;
		this.isSubtractable = subtractable;
		this.subtractionSet = set;
	}

	public static int getValue(String type) {
		
		return getNumeral(type).getValue();
	}

	public static RomanNumeral getNumeral(String romanNumeral) {

		RomanNumeral numeral = null;
		
		for(RomanNumeral rn : RomanNumeral.values()){
			if(rn.getType().equals(romanNumeral)){
				 numeral = rn;
				 break;
			}
		}
		
		return numeral;
	}
	
}
