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
	private static int sampleLength = Settings.getIndividualSize();

	public static double calculateCriticalD(HashMap<Integer, Integer> gapLengthsMap, int stringLength, int gapsNumericBase) throws IllegalArgumentException {
		if(stringLength < 1)
			throw new IllegalArgumentException("Illegal string length value: " + stringLength + ". Can't operate over a string of length < 1.");
		else if (gapsNumericBase < 2)
			throw new IllegalArgumentException("Illegal numeric value: " + gapsNumericBase + ". 2 (binary) is the smallest acceptable base.");

		Iterator<Entry<Integer, Integer>> itGapLengths = gapLengthsMap.entrySet().iterator();

		new DecimalFormat("#.####");
		double countGapLengthClass = 0, gapFrequency = 0;
		double gapRelativeFrequency = 0; // (gapFrequency / (binIndividualLength - 2))
		double gapCumRelativeFrequency = 0; // Sum of all gapFrequency
		double gapTheoreticalFrequency = 0; // F(x)
		double modulusTheoreticalMinusObservedFrequency = 0; // |F(x) - Sn(x)|
		double observedCriticalD = 0;

		if(itGapLengths.hasNext()) {
			//			System.out.println("Gap length || Frequency || Relative Frequency || Cum. Relative Frequency ||    F(x)    || |F(x) - Sn(x)|");
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

				//				System.out.println("     " + currentEntry.getKey() + "     ||"
				//						+ "    " + gapFrequency + "    ||"
				//						+ "      " + doubleFormat.format(gapRelativeFrequency) + "      ||"
				//						+ "          " + doubleFormat.format(gapCumRelativeFrequency) + "         ||"
				//						+ "    " + doubleFormat.format(gapTheoreticalFrequency) + "    ||"
				//						+ "  " + doubleFormat.format(modulusTheoreticalMinusObservedFrequency));

			}
		}
		//		System.out.println("\n\n maxObservedModulus = " + doubleFormat.format(observedCriticalD));
		//		System.out.println("=============================\n\n\n");

		gapLengthsMap.clear();

		return observedCriticalD;
	}

	public static boolean isFailToRejectNullHypothesis(double observedD) {
		boolean failsToReject = false;
		// According to the Kolmogorov-Smirnov Tables, for sample lengths N > 40, which is the case in this program,
		// the critical values for for alpha levels of significance 10%, 5%, 1% and 0.5% are, respectively, as follows:
		// (1.22/(N^(0.5))), (1.36/(N^(0.5))), (1.51/(N^(0.5))) and (1.63/(N^(0.5))).

		// Since the significance level being used is 5%, the observed D must be checked against the following

		double criticalValueForAlpha = 1.36 / Math.pow(sampleLength, 0.5);

		if (observedD < criticalValueForAlpha) {
			failsToReject = true;
		}

		return failsToReject;
	}
}
