package galaxy.controller;

import galaxy.exception.GalacticException;
import galaxy.model.GalacticNumeral;
import galaxy.model.Metal;
import galaxy.model.RomanNumeral;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CurrencyProcessorTest {

	private CurrencyProcessor processor;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init() throws GalacticException{
		processor = new CurrencyProcessor();
	}
	
	@Test
	public void shouldProcessAssignmentInfoRegexPattern(){
		
		String regex = "glob is I";
		processor.doFurtherProcessing(regex);
		
		GalacticNumeral galacticNumeral = new GalacticNumeral("glob");
		RomanNumeral romanNumeral = processor.getUtility().getCurrencyMappings().get(galacticNumeral);
		Assert.assertEquals(RomanNumeral.I, romanNumeral);
		
	}
	
	@Test
	public void shouldProcessCreditsInfoRegexPattern() throws GalacticException{
		
		String regex = "glob glob Silver is 34 Credits";
		processor.getUtility().addToCurrencyMapping("glob", "I");
		processor.doFurtherProcessing(regex);
		
		Metal silver = new Metal("Silver");
		boolean metalExists = processor.getUtility().getListOfMetals().contains(silver);
		Assert.assertEquals(true, metalExists);
	}
	
	@Test
	public void shouldProcessQueryHowMuchRegexPattern() throws GalacticException{
		
		String regex = "how much is pish tegj glob glob ?";
		processor.getUtility().addToCurrencyMapping("glob", "I");
		processor.getUtility().addToCurrencyMapping("pish", "X");
		processor.getUtility().addToCurrencyMapping("tegj", "V");
		
		processor.doFurtherProcessing(regex);
		
		Assert.assertEquals("pish tegj glob glob is 17", processor.getOutput().get(0));
	}
	
	@Test
	public void shouldProcessQueryHowManyRegexPattern() throws GalacticException{
		
		String regex = "how many Credits is glob prok Gold ?";
		
		processor.getUtility().addToCurrencyMapping("glob", "I");
		processor.getUtility().addToCurrencyMapping("prok", "V");
		
		processor.getUtility().addMetalToCollection("Gold", 20);
		
		processor.doFurtherProcessing(regex);
		
		Assert.assertEquals("glob prok Gold is 80 Credits", processor.getOutput().get(0));
	}

	@Test
	public void shouldNotProcessInvalidRegexPattern(){
		
		String regex = "glob is agasfga#$%";
		processor.doFurtherProcessing(regex);
		Assert.assertEquals("I have no idea what you are talking about", processor.getOutput().get(0));
		
		regex = "glob glob Silver is 34 678 Credits #$%^^$#";
		processor.doFurtherProcessing(regex);
		Assert.assertEquals("I have no idea what you are talking about", processor.getOutput().get(1));
		
		regex = "how much 345 is pish tegj glob glob ??????";
		processor.doFurtherProcessing(regex);
		Assert.assertEquals("I have no idea what you are talking about", processor.getOutput().get(2));
		
		regex = "how many many many Credits is glob prok Gold ?";
		processor.doFurtherProcessing(regex);
		Assert.assertEquals("I have no idea what you are talking about", processor.getOutput().get(3));
		
		regex = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?";
		processor.doFurtherProcessing(regex);
		Assert.assertEquals("I have no idea what you are talking about", processor.getOutput().get(4));
	}
}
