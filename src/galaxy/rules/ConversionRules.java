package galaxy.rules;

import galaxy.exception.GalacticExceptionType;
import galaxy.exception.IllegalConversionException;
import galaxy.exception.GalacticException;
import galaxy.model.RomanNumeral;

import java.util.ArrayList;
import java.util.List;

public class ConversionRules {

	public void checkRepition(List<RomanNumeral> listOfRn) throws IllegalConversionException {
		RomanNumeral previousRomanNumeral = null;
		int occurence = 0;
		for(int i = 0; i < listOfRn.size(); i++){
			if(listOfRn.get(i).equals(previousRomanNumeral)){
				checkIfRomanNumeralIsRepeatable(previousRomanNumeral);
				checkIfOccurenceExceeded(occurence);
				occurence += 1;
			}else{
				previousRomanNumeral = listOfRn.get(i);
				occurence = 1;
			}
		}
	}
	
	public List<Integer> checkSubtraction(List<RomanNumeral> listOfRn) throws IllegalConversionException {
		List<Integer> numbers = new ArrayList<Integer>();
		RomanNumeral romanNumeral;
		RomanNumeral nextRomanNumeral;
		int i;
		for(i = 0; i < listOfRn.size()-1; i++){
			romanNumeral = listOfRn.get(i);
			nextRomanNumeral = listOfRn.get(i+1);
			if(romanNumeral.getValue() < nextRomanNumeral.getValue()){
				checkIfNumeralIsSubtractable(romanNumeral);
				checkForValidSubtractionSet(romanNumeral, nextRomanNumeral);
				numbers.add(-romanNumeral.getValue());
			}else{
				numbers.add(romanNumeral.getValue());
			}
		}
		numbers.add(listOfRn.get(i).getValue());
		return numbers;
	}
	
	public List<RomanNumeral> checkValidity(String romanNumber) throws GalacticException {
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		for(int i = 0; i < romanNumber.length(); i++){
			String romanNumeral = String.valueOf(romanNumber.charAt(i));
			RomanNumeral rn = RomanNumeral.getNumeral(romanNumeral);
			checkForValidRomanNumeral(rn);
			listOfRn.add(rn);
		}
		return listOfRn;
	}

	private void checkIfNumeralIsSubtractable(RomanNumeral romanNumeral) throws IllegalConversionException {
		if(!romanNumeral.isSubtractable()) {
			throw new IllegalConversionException(GalacticExceptionType.SUBTRACTION_VIOLATION.getMessage());
		}
	}
	
	private void checkForValidSubtractionSet(RomanNumeral romanNumeral, RomanNumeral nextRomanNumeral) throws IllegalConversionException{
		if(!romanNumeral.getSubtractionSet().contains(nextRomanNumeral)) {
			throw new IllegalConversionException(GalacticExceptionType.SUBTRACTION_SET_INVALID_VIOLATION.getMessage());
		}
	}
	
	private void checkForValidRomanNumeral(RomanNumeral rn) throws GalacticException{
		if(rn==null){
			throw new GalacticException(GalacticExceptionType.INVALID_ROMAN_NUMERAL_VIOLATION.getMessage());
		}
	}
	
	private void checkIfOccurenceExceeded(int occurence) throws IllegalConversionException  {
		if(!(occurence < RomanNumeral.MAX_OCCURENCE_IN_SUCCESSION)){
			throw new IllegalConversionException(GalacticExceptionType.REPETITION_OCCURENCE_VIOLATION.getMessage());
		}
	}

	private void checkIfRomanNumeralIsRepeatable(RomanNumeral previousRomanNumeral) throws IllegalConversionException   {
		if(!previousRomanNumeral.isRepeatable()){
			throw new IllegalConversionException(GalacticExceptionType.REPETITION_VIOLATION.getMessage());
		}
	}

}
