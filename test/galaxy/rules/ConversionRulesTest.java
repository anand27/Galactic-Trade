package galaxy.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import galaxy.exception.GalacticException;
import galaxy.exception.GalacticExceptionType;
import galaxy.exception.IllegalConversionException;
import galaxy.model.RomanNumeral;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConversionRulesTest {

	private ConversionRules rules;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		rules = new ConversionRules();
	}
	
	@Test
	public void shouldCheckForValidRepetition() throws IllegalConversionException{
		
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		listOfRn.add(RomanNumeral.C);
		listOfRn.add(RomanNumeral.C);
		listOfRn.add(RomanNumeral.C);
		
		rules.checkRepition(listOfRn);
		
		assertNotNull(listOfRn);
	}

	@Test
	public void shouldThrowExceptionForNonRepeatableRomanNumeral() throws IllegalConversionException{
		exception.expect(IllegalConversionException.class);
		exception.expectMessage(GalacticExceptionType.REPETITION_VIOLATION.getMessage());
		
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		listOfRn.add(RomanNumeral.D);
		listOfRn.add(RomanNumeral.D);
		
		rules.checkRepition(listOfRn);
	}
	
	@Test
	public void shouldThrowExceptionIfOccurenceExceedsForRepeatableRomanNumeral() throws IllegalConversionException{
		exception.expect(IllegalConversionException.class);
		exception.expectMessage(GalacticExceptionType.REPETITION_OCCURENCE_VIOLATION.getMessage());
		
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		listOfRn.add(RomanNumeral.C);
		listOfRn.add(RomanNumeral.C);
		listOfRn.add(RomanNumeral.C);
		listOfRn.add(RomanNumeral.C);
		
		rules.checkRepition(listOfRn);
	}
	
	@Test
	public void shouldCheckForValidRomanNumeral() throws GalacticException{
		
		String romanNumerals = "XXIV";
		List<RomanNumeral> list = rules.checkValidity(romanNumerals);
		
		assertNotNull(list);
		assertSame(RomanNumeral.X, list.get(0));
		assertSame(RomanNumeral.X, list.get(1));
		assertSame(RomanNumeral.I, list.get(2));
		assertSame(RomanNumeral.V, list.get(3));
		
	}
	
	@Test
	public void shouldThrowExceptionForInvalidRomanNumeral() throws GalacticException{
		exception.expect(GalacticException.class);
		exception.expectMessage(GalacticExceptionType.INVALID_ROMAN_NUMERAL_VIOLATION.getMessage());
		
		String romanNumerals = "PPLL#$";
		List<RomanNumeral> list = rules.checkValidity(romanNumerals);
		
		assertNotNull(list);
	}
	
	@Test
	public void shouldCheckForValidSubtraction() throws IllegalConversionException{
		
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		listOfRn.add(RomanNumeral.X);
		listOfRn.add(RomanNumeral.X);
		listOfRn.add(RomanNumeral.I);
		listOfRn.add(RomanNumeral.V);
		
		List<Integer> numbers = rules.checkSubtraction(listOfRn);
		
		assertNotNull(numbers);
		assertSame(10, numbers.get(0));
		assertSame(10, numbers.get(1));
		assertSame(-1, numbers.get(2));
		assertSame(5, numbers.get(3));
		
	}
	
	@Test
	public void shouldThrowExceptionForNonSubtractableNumeral() throws IllegalConversionException{
		exception.expect(IllegalConversionException.class);
		exception.expectMessage(GalacticExceptionType.SUBTRACTION_VIOLATION.getMessage());
		
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		listOfRn.add(RomanNumeral.D);
		listOfRn.add(RomanNumeral.M);
		listOfRn.add(RomanNumeral.I);
		listOfRn.add(RomanNumeral.V);
		
		List<Integer> numbers = rules.checkSubtraction(listOfRn);
		
		assertNotNull(numbers);
	}
	
	@Test
	public void shouldThrowExceptionForInvalidSubtractionSet() throws IllegalConversionException{
		exception.expect(IllegalConversionException.class);
		exception.expectMessage(GalacticExceptionType.SUBTRACTION_SET_INVALID_VIOLATION.getMessage());
		
		List<RomanNumeral> listOfRn = new ArrayList<RomanNumeral>();
		listOfRn.add(RomanNumeral.C);
		listOfRn.add(RomanNumeral.D);
		listOfRn.add(RomanNumeral.X);
		listOfRn.add(RomanNumeral.L);
		listOfRn.add(RomanNumeral.I);
		listOfRn.add(RomanNumeral.L);
		
		List<Integer> numbers = rules.checkSubtraction(listOfRn);
		
		assertNotNull(numbers);
	}
}
