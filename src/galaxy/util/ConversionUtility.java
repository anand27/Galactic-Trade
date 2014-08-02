package galaxy.util;

import galaxy.exception.GalacticException;
import galaxy.exception.GalacticExceptionType;
import galaxy.exception.IllegalConversionException;
import galaxy.model.GalacticNumeral;
import galaxy.model.Metal;
import galaxy.model.RomanNumeral;
import galaxy.rules.ConversionRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversionUtility{

	private ConversionRules rules;
	private Set<Metal> listOfMetals;
	private Map<GalacticNumeral, RomanNumeral> currencyMappings;
	
	public ConversionUtility() {
		this.rules = new ConversionRules();
		currencyMappings = new HashMap<GalacticNumeral, RomanNumeral>();
		listOfMetals = new HashSet<Metal>();
	}
	
	public String galacticToRoman(String[] galCurrency) throws GalacticException {
		List<GalacticNumeral> galacticNumerals = convertToGalacticNumeralObjects(galCurrency);
		StringBuilder sb = new StringBuilder();
		RomanNumeral romanNumeral;
		for(GalacticNumeral galacticNumeral : galacticNumerals){
			romanNumeral = this.currencyMappings.get(galacticNumeral);
			if(null!=romanNumeral)
				sb.append(romanNumeral);
			else
				throw new GalacticException(GalacticExceptionType.INVALID_GALACTIC_NUMERAL_VIOLATION.getMessage());
		}
		String romanNumber = sb.toString();
		return romanNumber;
	}
	
	private List<GalacticNumeral> convertToGalacticNumeralObjects(String[] galCurrency) {
		List<GalacticNumeral> galacticNumerals = new ArrayList<GalacticNumeral>();
		for(String num : galCurrency){
			galacticNumerals.add(new GalacticNumeral(num));
		}
		return galacticNumerals;
	}

	public int romanToDecimalConversion(String romanNumber) throws GalacticException, IllegalConversionException {
		List<RomanNumeral> listOfRn = rules.checkValidity(romanNumber);
		rules.checkRepition(listOfRn);
		List<Integer> numbers = rules.checkSubtraction(listOfRn);
		int total = evaluate(numbers);
		return total;
	}

	private int evaluate(List<Integer> numbers) {
		int total = 0;
		for(Integer num : numbers){
			total += num;
		}
		return total;
	}

	public void addToCurrencyMapping(String galacticNum, String romanNum) throws GalacticException {
		GalacticNumeral galacticNumeral = new GalacticNumeral(galacticNum);
		RomanNumeral romanNumeral = RomanNumeral.getNumeral(romanNum);
		this.currencyMappings.put(galacticNumeral, romanNumeral);
	}

	public void addMetalToCollection(String metalName, int unitValueOfMetal) {
		Metal metal = new Metal(metalName, unitValueOfMetal);
		this.listOfMetals.add(metal);
	}

	public int getMetalPrice(String metalName) throws GalacticException {
		Metal metalToBeSearched = new Metal(metalName);
		Metal metalFromSet = null;
		int unitPrice = -1;
		Iterator<Metal> iterator = this.listOfMetals.iterator();
		while(iterator.hasNext()){
			metalFromSet = iterator.next();
			if(metalToBeSearched.equals(metalFromSet)){
				unitPrice = metalFromSet.getUnitPrice();
				break;
			}
		}
		if(unitPrice==-1)
			throw new GalacticException(GalacticExceptionType.INVALID_METAL.getMessage());
		
		return unitPrice;
	}

	public Set<Metal> getListOfMetals() {
		return listOfMetals;
	}

	public Map<GalacticNumeral, RomanNumeral> getCurrencyMappings() {
		return currencyMappings;
	}
	
	
}
