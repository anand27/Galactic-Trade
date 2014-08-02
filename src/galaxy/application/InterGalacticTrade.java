package galaxy.application;

import galaxy.controller.CurrencyProcessor;

import java.io.IOException;

public class InterGalacticTrade {

	public static void main(String[] args) throws IOException {
		
		CurrencyProcessor processor = new CurrencyProcessor();
		processor.readInput();
		processor.doProcessing();
	}
}
