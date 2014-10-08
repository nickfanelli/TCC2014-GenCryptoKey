package br.pucc.engComp.GenCryptoKey.controller;

import java.util.HashMap;

public class GapTest {

	/**
	 * Performs counting of gaps and their respective frequency in the given string
	 * 
	 * @param individual string whose gaps and their lengths will be counted
	 * @param individualBase the numeric base the string is in (2, for binary; 10, for decimal; etc)
	 * 
	 * @return HashMap of gap lengths and their respective frequency within the given string
	 */
	public static HashMap<Integer, Integer> countGaps(String individual, int individualNumericBase) throws IllegalArgumentException {
		if(individualNumericBase < 0)
			throw new IllegalArgumentException("Illegal numeric base value: " + individualNumericBase + ". It must be a positive integer.");

		//		HashMap<Integer, Integer> numOfGapsPerDigit = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> gapLengths = new HashMap<Integer, Integer>();

		//		numOfGapsPerDigit.entrySet().iterator();
		gapLengths.entrySet().iterator();

		int individualLength = individual.length();

		char digit;
		int gapLength = 0;
		boolean foundFirstDigit = false;

		for(int i = 0; i <= 1; i++) {
			// convert i to char, for later comparison
			digit = Character.forDigit(i, individualNumericBase);
			gapLength = 0;
			foundFirstDigit = false;
			for(int j = 0; j < individualLength; j++) {
				if(individual.charAt(j) == digit && !foundFirstDigit) { // Look for the first occurrence of digit 'k'
					foundFirstDigit = true;
					gapLength = 0;
				} else if(individual.charAt(j) == digit && foundFirstDigit) { // Found the second occurrence
					Integer gapLengthKey = new Integer(gapLength);

					Integer gapLengthValue = gapLengths.get(gapLengthKey);
					if(gapLengthValue == null) {
						gapLengths.put(gapLengthKey, 1);
					} else {
						gapLengths.put(gapLengthKey, gapLengthValue + 1);
					}

					gapLength = 0;
				} else { // Still not equal to digit, keep counting gap length
					gapLength++;
				}
			}
		}

		return gapLengths;
	}
}
