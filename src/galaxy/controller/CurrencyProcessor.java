package galaxy.controller;

import galaxy.exception.GalacticException;
import galaxy.exception.IllegalConversionException;
import galaxy.util.ConversionUtility;
import galaxy.util.RegexPattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class CurrencyProcessor {

	private static final String INPUT_FILE = "/Input.txt";
	
	private BufferedReader br;
	private List<String> output;
	private ConversionUtility utility;
	
	public CurrencyProcessor() {
		utility = new ConversionUtility();
		output = new ArrayList<String>();
	}
	
	public void readInput() throws IOException{
		
		System.out.println("Press 1 - Manual Input from keyboard");
		System.out.println("Press 2 - Read   Input from Classpath");
		Scanner ob = new Scanner(System.in);
		int val = ob.nextInt();
		switch(val){
		
		case 1:		
					System.out.println("Type expression and press enter.");
					System.out.println(" OR ");
					System.out.println("Type 'exit' to quit.");
					this.br = new BufferedReader(new InputStreamReader(System.in));
					String name = null;
					while(!(name=br.readLine()).equals("exit")){
						doFurtherProcessing(name);
						if(output.size()>0){
							System.out.println(output.get(0));
							output.clear();
						}else{
							System.out.println("kindly continue...");
						}
					}
					System.exit(0);
					break;
		case 2:
					InputStream in = this.getClass().getResourceAsStream(INPUT_FILE);
					this.br = new BufferedReader(new InputStreamReader(in));
					break;
		default : 
					System.out.println("Retry running program with valid inout");
					System.exit(0);
					break;
		}
	}

	public void doProcessing() {
		String line;
		try {
			System.out.println(DELIMITER);
			
			while((line = br.readLine())!=null){
				System.out.println(line);
				doFurtherProcessing(line);
			}
			
			System.out.println(DELIMITER);
			
			for(String output : this.output){
				System.out.println(output);
			}
			
			System.out.println(DELIMITER);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void doFurtherProcessing(String line) {
		boolean processed = false;
		for(RegexPattern rp : RegexPattern.values()){
			Matcher matcher = rp.computeMatcher(line);
			if(matcher.matches()){
				try {
					operateOnInput(rp);
				} catch (GalacticException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				} catch (IllegalConversionException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				} 
				processed = true;
			}
		 }
		
		if(!processed)
			this.output.add("I have no idea what you are talking about");
	}
	
	
	private void operateOnInput(RegexPattern pattern) throws GalacticException, IllegalConversionException{
		String galacticNum;
		String romanNum;
		String[] galacticCurrencies;
		String metalName;
		
		switch(pattern){
			case INFO_ASSIGNMENT:   
									galacticNum = pattern.getMatcher().group(1);
									romanNum = pattern.getMatcher().group(2);
									utility.addToCurrencyMapping(galacticNum, romanNum);
									break;
			case INFO_CREDITS: 
									galacticCurrencies = pattern.getMatcher().group(1).split("\\s");
									metalName = pattern.getMatcher().group(2);
									int totalValue = Integer.parseInt(pattern.getMatcher().group(3));
									int divisor = processConversion(galacticCurrencies);
									int unitValueOfMetal = totalValue / divisor;
									utility.addMetalToCollection(metalName, unitValueOfMetal);
									break;
			case QUERY_HOW_MUCH: 
									galacticCurrencies = pattern.getMatcher().group(1).split("\\s");
									int value = processConversion(galacticCurrencies);
									this.output.add(pattern.getMatcher().group(1) + "is " + value);
									break;
			case QUERY_HOW_MANY: 
									galacticCurrencies = pattern.getMatcher().group(2).split("\\s");
									metalName = pattern.getMatcher().group(3);
									int count = processConversion(galacticCurrencies);
									int total = count * utility.getMetalPrice(metalName);
									this.output.add(pattern.getMatcher().group(2) + pattern.getMatcher().group(3) + " is " + total + " " + pattern.getMatcher().group(1));
									break;
		
		}
	}
	
	private int processConversion(String[] galCurrency) throws GalacticException, IllegalConversionException{
		String romanNumber = utility.galacticToRoman(galCurrency);
		int decimal = utility.romanToDecimalConversion(romanNumber);
		return decimal;
	}
	
	public ConversionUtility getUtility() {
		return utility;
	}

	public List<String> getOutput() {
		return output;
	}
	
	private static final String DELIMITER = "------------------------------------------";
	
}
