package br.pucc.engComp.GenCryptoKey.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Performs the Kolmogorov-Smirnov test upon a set of gap lengths from any given string.
 * 
 * @param gapLengthsMap map of gap lengths of respective frequencies within a given string
 * @param stringLength length of the string over which the counting of gaps was made
 * @param gapsNumericBase the numeric base of the string from which the gap counting was made (2, for binary; 10, for decimal; etc)
 * 
 * @return observedCriticalD the largest absolute difference between theoretical and observed frequencies on the given set
 */
public class KolmogorovSmirnovTest {

	public static double calculateCriticalD(HashMap<Integer, Integer> gapLengthsMap, int stringLength, int gapsNumericBase) throws IllegalArgumentException {
		if(stringLength < 1)
			throw new IllegalArgumentException("Illegal string length value: " + stringLength + ". Can't operate over a string of length < 1.");
		else if (gapsNumericBase < 2)
			throw new IllegalArgumentException("Illegal numeric value: " + gapsNumericBase + ". 2 (binary) is the smallest acceptable base.");

		Iterator<Entry<Integer, Integer>> itGapLengths = gapLengthsMap.entrySet().iterator();

		DecimalFormat doubleFormat = new DecimalFormat("#.####");
		double countGapLengthClass = 0, gapFrequency = 0;
		double gapRelativeFrequency = 0; // (gapFrequency / (binIndividualLength - 2))
		double gapCumRelativeFrequency = 0; // Sum of all gapFrequency
		double gapTheoreticalFrequency = 0; // F(x)
		double modulusTheoreticalMinusObservedFrequency = 0; // |F(x) - Sn(x)|
		double observedCriticalD = 0;

		if(itGapLengths.hasNext()) {
			System.out.println("Gap length || Frequency || Relative Frequency || Cum. Relative Frequency ||    F(x)    || |F(x) - Sn(x)|");
			while(itGapLengths.hasNext()) {
				Map.Entry<Integer, Integer> currentEntry = itGapLengths.next();

				countGapLengthClass++;
				gapFrequency = currentEntry.getValue();
				gapRelativeFrequency = (gapFrequency / (stringLength - gapsNumericBase));
				gapCumRelativeFrequency = gapCumRelativeFrequency + gapRelativeFrequency;
				gapTheoreticalFrequency = 1 - Math.pow(0.9, countGapLengthClass + 1);
				modulusTheoreticalMinusObservedFrequency = Math.abs(gapTheoreticalFrequency - gapCumRelativeFrequency);

				if(modulusTheoreticalMinusObservedFrequency > observedCriticalD) {
					observedCriticalD = modulusTheoreticalMinusObservedFrequency;
				}

				System.out.println("     " + currentEntry.getKey() + "     ||"
						+ "    " + gapFrequency + "    ||"
						+ "      " + doubleFormat.format(gapRelativeFrequency) + "      ||"
						+ "          " + doubleFormat.format(gapCumRelativeFrequency) + "         ||"
						+ "    " + doubleFormat.format(gapTheoreticalFrequency) + "    ||"
						+ "  " + doubleFormat.format(modulusTheoreticalMinusObservedFrequency));

			}
		}
		System.out.println("\n\n maxObservedModulus = " + doubleFormat.format(observedCriticalD));
		System.out.println("=============================\n\n\n");

		gapLengthsMap.clear();

		return observedCriticalD;
	}
}
