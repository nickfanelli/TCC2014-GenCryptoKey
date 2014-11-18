package br.pucc.engComp.GenCryptoKey.controller;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class PearsonsChiSquareTest {
	private static double alphaSignificanceLevel = 0.05;
	private static double theoreticalChiSquareValueForAlpha = 3.841; // degrees of freedom = 1 (it is the num. of classes - 1)

	private PearsonsChiSquareTest(){} // suppressing the class constructor

	public static boolean ChiSquare(double[] expectedFrequencies, long[] observedFrequencies) {
		ChiSquareTest chiSq = new ChiSquareTest();
		return chiSq.chiSquareTest(expectedFrequencies, observedFrequencies, alphaSignificanceLevel);
	}

	public static double ChiSquare(long[] observedFrequencies, long[] expectedFrequencies) {
		double chiSquareResult = 0;
		int numOfCategories = 0;

		numOfCategories = observedFrequencies.length;

		for(int i = 0; i < numOfCategories; i++) {
			chiSquareResult += Math.pow((observedFrequencies[i] - expectedFrequencies[i]), 2) / expectedFrequencies[i];
		}
		//		System.out.println("ChiSquareResult: " + chiSquareResult);

		return chiSquareResult;
	}

	public static double ChiSquareShannonEntropy(double observedEntropy, double expectedEntropy) {
		double chiSquareShannonEntropyResult = 0;

		chiSquareShannonEntropyResult = Math.pow((observedEntropy - expectedEntropy), 2) / expectedEntropy;

		//		System.out.println("chiSquareShannonEntropyResult: " + chiSquareShannonEntropyResult);

		return chiSquareShannonEntropyResult;
	}

	public static boolean isFailToRejectNullHypothesis(double chiSquareResult) {
		boolean failsToReject = false;

		if(chiSquareResult < theoreticalChiSquareValueForAlpha) {
			failsToReject = true;
		}

		return failsToReject;
	}

	public static long[] calculateObservedFrequencies(Individual individual) {
		long[] observedFrequencies = new long[]{0, 0};
		// observedFrequencies[0] = frequency of 0s
		// observedFrequencies[1] = frequency of 1s

		//		System.out.println("getBinaryStringSize(): " + individual.getBinaryStringSize());
		for(int i = 0; i < individual.getBinaryStringSize(); i++) {
			if(individual.toBinaryString().charAt(i) == '0') {
				observedFrequencies[0] += 1; // increment frequency of 0s
			} else {
				observedFrequencies[1] += 1; // increment frequency of 1s
			}
		}
		//		System.out.println("number of 0s: " + observedFrequencies[0]);
		//		System.out.println("number of 1s: " + observedFrequencies[1]);
		return observedFrequencies;
	}
}
