package br.pucc.engComp.GenCryptoKey.controller;

import java.util.HashMap;

public class GeneticAlgorithm {

	// Evolve the population
	public static void evolvePopulation(Population2 pop) {

		//evaluateFitness(pop);

		// Creation of new individuals through crossover using the current population
		crossover(pop);

		// Mutation
		for (int i = 0; i < pop.getSize(); i++) {
			mutate(pop.getIndividual(i));
		}

		// Calculate fitness values for population's individuals
		evaluateFitness(pop);

		// Selection of current fittest individuals
		rankSelection(pop);
	}

	/**
	 * Fitness method.
	 * @param pop population whose individuals are going to have their fitness evaluated
	 */
	public static void evaluateFitness(Population2 pop) {

		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
			try {
				HashMap<Integer, Integer> gapLengthsMap = GapTest.countGaps(pop.getIndividual(i).toBinaryString(), 2);
				pop.getIndividual(i).setFitnessValue(KolmogorovSmirnovTest.calculateCriticalD(gapLengthsMap, pop.getIndividual(i).getBinaryStringSize(), 2));
			} catch (IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
			}

		}

		/** OLD CODE BELOW */

		//		HashMap<BigInteger, String> nInBinaryMap = new HashMap<BigInteger, String>();
		//		HashMap<BigInteger, Double> nFitnessMap = new HashMap<BigInteger, Double>();
		//		BigInteger n;
		//		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
		//			// n = (p * q)
		//			// Each individual's 'n' is converted to Binary and stored in nInBinaryMap for future evaluation
		//			n = pop.getIndividual(i).getP().multiply(pop.getIndividual(i).getQ());
		//			nInBinaryMap.put(n, n.toString(2));
		//		}
		//
		//		Iterator<Entry<BigInteger, String>> it = nInBinaryMap.entrySet().iterator();
		//		while(it.hasNext()) {
		//			Map.Entry<BigInteger, String> entry = it.next();
		//
		//			nFitnessMap.put(entry.getKey(), gapTest(entry.getValue()) + frequencyTest(entry.getValue()));
		//		}
		//
		//		return nFitnessMap;
	}

	//	private static double gapTest(String individual) {
	//
	//		/**
	//		 * GAP TEST for the BINARY representation of N
	//		 */
	//		HashMap<Integer, Integer> numOfGapsPerDigit = new HashMap<Integer, Integer>();
	//		HashMap<Integer, Integer> gapLengths = new HashMap<Integer, Integer>();
	//
	//		numOfGapsPerDigit.entrySet().iterator();
	//		gapLengths.entrySet().iterator();
	//
	//		int individualLength = individual.length();
	//
	//		char digit;
	//		int gapLength = 0, gapCount = 0;
	//		boolean foundFirstDigit = false;
	//
	//		for(int i = 0; i <= 1; i++) {
	//			// convert i to char, for later comparison
	//			digit = Character.forDigit(i, 2);
	//			gapLength = 0;
	//			foundFirstDigit = false;
	//			for(int j = 0; j < individualLength; j++) {
	//				if(individual.charAt(j) == digit && !foundFirstDigit) { // Look for the first occurrence of digit 'k'
	//					foundFirstDigit = true;
	//					gapLength = 0;
	//				} else if(individual.charAt(j) == digit && foundFirstDigit) { // Found the second occurrence
	//					gapCount++;
	//					Integer gapLengthKey = new Integer(gapLength);
	//
	//					Integer gapLengthValue = gapLengths.get(gapLengthKey);
	//					if(gapLengthValue == null) {
	//						gapLengths.put(gapLengthKey, 1);
	//					} else {
	//						gapLengths.put(gapLengthKey, gapLengthValue + 1);
	//					}
	//
	//					gapLength = 0;
	//				} else { // Still not equal to digit, keep counting gap length
	//					gapLength++;
	//				}
	//			}
	//			numOfGapsPerDigit.put(i, gapCount);
	//			gapCount = 0;
	//		}
	//
	//
	//		Iterator<Entry<Integer, Integer>> itNumOfGapsPerDigit = numOfGapsPerDigit.entrySet().iterator();
	//		while(itNumOfGapsPerDigit.hasNext()) {
	//			Map.Entry<Integer, Integer> entry = itNumOfGapsPerDigit.next();
	//
	//			System.out.println("Digit " + entry.getKey() + " has " + entry.getValue() + " gaps.");
	//		}
	//
	//		System.out.println("This individual has " + gapLengths.size() + " different gap lengths.");
	//
	//		Iterator<Entry<Integer, Integer>> itGapLengths = gapLengths.entrySet().iterator();
	//
	//		DecimalFormat doubleFormat = new DecimalFormat("#.####");
	//		double countGapLengthClass = 0, gapFrequency = 0;
	//		double gapRelativeFrequency = 0; // (gapFrequency / (binIndividualLength - 2))
	//		double gapCumRelativeFrequency = 0; // Sum of all gapFrequency
	//		double gapTheoreticalFrequency = 0; // F(x)
	//		double modulusTheoreticalMinusObservedFrequency = 0; // |F(x) - Sn(x)|
	//		double maxObservedModulus = 0;
	//
	//		if(itGapLengths.hasNext()) {
	//			System.out.println("GapCumRelativeFrequency = " + gapCumRelativeFrequency);
	//			System.out.println("Gap length || Frequency || Relative Frequency || Cum. Relative Frequency ||    F(x)    || |F(x) - Sn(x)|");
	//			while(itGapLengths.hasNext()) {
	//				Map.Entry<Integer, Integer> currentEntry = itGapLengths.next();
	//
	//				countGapLengthClass++;
	//				gapFrequency = currentEntry.getValue();
	//				gapRelativeFrequency = (gapFrequency / (individualLength - 2));
	//				gapCumRelativeFrequency = gapCumRelativeFrequency + gapRelativeFrequency;
	//				gapTheoreticalFrequency = 1 - Math.pow(0.9, countGapLengthClass + 1);
	//				modulusTheoreticalMinusObservedFrequency = Math.abs(gapTheoreticalFrequency - gapCumRelativeFrequency);
	//
	//				if(modulusTheoreticalMinusObservedFrequency > maxObservedModulus) {
	//					maxObservedModulus = modulusTheoreticalMinusObservedFrequency;
	//				}
	//
	//				System.out.println("     " + currentEntry.getKey() + "     ||"
	//						+ "    " + gapFrequency + "    ||"
	//						+ "      " + doubleFormat.format(gapRelativeFrequency) + "      ||"
	//						+ "          " + doubleFormat.format(gapCumRelativeFrequency) + "         ||"
	//						+ "    " + doubleFormat.format(gapTheoreticalFrequency) + "    ||"
	//						+ "  " + doubleFormat.format(modulusTheoreticalMinusObservedFrequency));
	//
	//			}
	//		}
	//		System.out.println("\n\n maxObservedModulus = " + doubleFormat.format(maxObservedModulus));
	//		System.out.println("=====================\n\n\n");
	//
	//		numOfGapsPerDigit.clear();
	//		gapLengths.clear();
	//
	//		return maxObservedModulus;
	//	}

	private static int frequencyTest(String binaryString) {
		int frequencyFitness = 0;
		// TODO
		return frequencyFitness;
	}

	/**
	 * Crossover method.
	 * @param pop population over which the crossover operation is going to occur
	 */
	private static void crossover(Population2 pop) {
		// Loop through all individuals' applying the crossover to generate new individuals
		// Repeated crossovers are disregarded, thus n^2/2 operations are made in O(n)
		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
			for(int j = i+1; j < Settings.getMaxPopulationSize(); ++j) {
				// There is no need to choose a random crossover point, since
				// the individual only has 2 genes to work with (p, q)
				Individual2 ind1 = pop.newIndividual();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Individual2 ind2 = pop.newIndividual();

				// Population example: I1: {p1, q1} ; I2: {p2, q2} ; I3: {p3, q3} ; ... ; In: {pn, qn}
				// Offspring after crossover of I1 and I2:
				// C1: {p1, q2}; C2: {p2, q1}
				// TODO It is possible to generate 2 more individuals in the offspring: C3: {p1, p2}; C4: {q1, q2}

				ind1.setP(pop.getIndividual(i).getP());
				ind1.setQ(pop.getIndividual(j).getQ());

				ind2.setP(pop.getIndividual(j).getP());
				ind2.setP(pop.getIndividual(i).getQ());
			}
		}
	}


	/**
	 * Mutation method.
	 * @param indiv individual to possibly have a gene mutated
	 */
	private static void mutate(Individual2 indiv) {
		// If mutation chance passes (meaning it will occur)...
		if (Math.random() <= Settings.getMutationRate()) {
			// ...draw a random gene to be modified (0 or 1 == P or Q), since the 3rd gene (public exponent E) won't be modified.
			//			(int) Math.floor(Math.random() * Settings.getIndividualSize()
			int randomGene = (int) Math.floor(Math.random() * (indiv.getIndividual().size()-1));

			// Mutation is performed by obtaining the next probable prime as placing it as the new value of the mutated gene
			// It will never be the same as number as the original gene.
			indiv.getIndividual().set(randomGene, indiv.getIndividual().get(randomGene).nextProbablePrime());

			/**
			 * It can also be done choosing a new random prime, not necessarily the next probable.
			 * If such approach is used, it must be checked whether the generated number isn't the same as the original gene.
			 */
			/*
			BigInteger newProbablePrime = BigInteger.probablePrime(Settings.getIndividualSize() / 2, new SecureRandom());

			while (indiv.getIndividual().get(randomGene) == newProbablePrime) {
				newProbablePrime = BigInteger.probablePrime(Settings.getIndividualSize() / 2, new SecureRandom());
			}
			indiv.getIndividual().set(randomGene, newProbablePrime);
			 */
		}
	}


	/**
	 * Selection method.
	 * @param pop population from which individuals are going to be selected based on their fitness values
	 */
	private static void rankSelection(Population2 pop) {
		// Sorts the current population in descending order of fitness
		pop.orderByFitness();

		// Wipes the rest of the population
		while(pop.getSize() > Settings.getMaxPopulationSize()) {
			pop.kill(Settings.getMaxPopulationSize());
		}

		// After this, only the [MaxPopulationSize] individuals will be present on the population
	}
}
