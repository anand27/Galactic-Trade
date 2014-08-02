package galaxy.util;

import galaxy.exception.GalacticException;
import galaxy.exception.GalacticExceptionType;
import galaxy.exception.IllegalConversionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConversionUtilityTest {

	private ConversionUtility utility;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init() throws GalacticException{
		utility = new ConversionUtility();
		
		utility.addToCurrencyMapping("glob", "I");
		utility.addToCurrencyMapping("prok", "V");
		utility.addToCurrencyMapping("pish", "X");
		utility.addToCurrencyMapping("tejg", "C");
		
		utility.addMetalToCollection("Silver", 10);
		utility.addMetalToCollection("Iron", 20);
		utility.addMetalToCollection("Gold", 30);
	}
	
	@Test
	public void shouldConvertGalacticToRomanNumeral() throws GalacticException{
		
		String[] galCurrency = new String[]{"glob", "prok", "pish"};
		String galacticToRoman = utility.galacticToRoman(galCurrency);
		Assert.assertEquals(galacticToRoman, "IVX");
		
		galCurrency = new String[]{"tejg", "glob", "glob", "prok"};
		galacticToRoman = utility.galacticToRoman(galCurrency);
		Assert.assertEquals(galacticToRoman, "CIIV");
	}
	
	@Test
	public void shouldThrowExceptionForInvalidConversionOfGalacticToRomanNumeral() throws GalacticException{
		
		exception.expect(GalacticException.class);
		exception.expectMessage(GalacticExceptionType.INVALID_GALACTIC_NUMERAL_VIOLATION.getMessage());
		
		String[] galCurrency = new String[]{"glob", "aaaaaaaaaaaa", "pish"};
		utility.galacticToRoman(galCurrency);
		
	}
	
	@Test
	public void shouldConvertRomanToDecimal() throws GalacticException, IllegalConversionException{
		
		String romanNumber = "XXXIV";
		int decimalValue = utility.romanToDecimalConversion(romanNumber);
		Assert.assertSame(34, decimalValue);
		
		romanNumber = "MCMXLIV";
		decimalValue = utility.romanToDecimalConversion(romanNumber);
		Assert.assertEquals(1944, decimalValue);
		
	}
	
	@Test
	public void shouldGetMetalPrice() throws GalacticException{
		
		int metalPriceForSilver = utility.getMetalPrice("Silver");
		Assert.assertSame(10, metalPriceForSilver);
		
		int metalPriceForGold = utility.getMetalPrice("Gold");
		Assert.assertSame(30, metalPriceForGold);
		
		int metalPriceForIron = utility.getMetalPrice("Iron");
		Assert.assertSame(20, metalPriceForIron);
		
	}
	
	@Test
	public void shouldThrowExceptionForInvalidMetal() throws GalacticException{
		
		exception.expect(GalacticException.class);
		exception.expectMessage(GalacticExceptionType.INVALID_METAL.getMessage());
		
		int metalPriceForIron = utility.getMetalPrice("URANIUM");
		
	}
}
