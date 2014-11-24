package br.pucc.engComp.GenCryptoKey.controller;

import java.util.HashMap;

public class GeneticAlgorithm {

	// Evolve the population
	public static void evolvePopulation(Population pop) {
		long tInicial = System.currentTimeMillis();

		// Creation of new individuals through crossover using the current population
		//		System.out.println("[LOG - DEBUG] -- Crossover starting!");
		crossover(pop);
		//		System.out.println("[LOG - DEBUG] -- Crossover ended!");
		long tCrossEn = System.currentTimeMillis();

		// Mutation
		//		System.out.println("[LOG - DEBUG] -- Mutation starting!");
		//		System.out.println("[LOG - DEBUG] -- pop.getSize() = " + pop.getSize());
		for (int i = 0; i < pop.getSize(); i++) {
			mutate(pop.getIndividual(i));
			//			System.out.println("[LOG - DEBUG] -- Mutação iteração: " + i);
		}
		//		System.out.println("[LOG - DEBUG] -- Mutation ended!");
		long tMutateEn = System.currentTimeMillis();

		// Calculate fitness values for population's individual
		//		System.out.println("[LOG - DEBUG] -- Fitness starting!");
		evaluateFitness(pop);
		//		System.out.println("[LOG - DEBUG] -- Fitness ended!");
		long tEvEnd = System.currentTimeMillis();

		// Selection of current fittest individuals
		//		System.out.println("[LOG - DEBUG] -- Rank starting!");
		rankSelection(pop);
		//		System.out.println("[LOG - DEBUG] -- Rank ended");
		long tRankEn = System.currentTimeMillis();

		long tElapsed = tRankEn - tInicial;
		long durCross = tCrossEn - tInicial;
		long durMutate = tMutateEn - tCrossEn;
		long durEvalua = tEvEnd - tMutateEn;
		long durRank = tRankEn - tEvEnd;

		System.out.println("[LOG - DEBUG] -- Crossover duration: " + durCross/(double)tElapsed + "%");
		System.out.println("[LOG - DEBUG] -- Mutation duration: " + durMutate/(double)tElapsed + "%");
		System.out.println("[LOG - DEBUG] -- Evaluation duration: " + durEvalua/(double)tElapsed + "%");
		System.out.println("[LOG - DEBUG] -- Ranking duration: " + durRank/(double)tElapsed + "%");
	}

	/**
	 * Fitness method.<br>
	 * <i>H<sub>0</sub></i> : the distribution of 0s and 1s in the analyzed {@code Individual}s is random.<br>
	 * <i>H<sub>1</sub></i> : the distribution of 0s and 1s in the analyzed {@code Individual}s is not random.<br>
	 * Sets the {@code Individual}'s fitness value as the Euclidian Norm of
	 * all applied fitness tests' results.
	 * @param pop population whose individuals are going to have their fitness evaluated.
	 */
	public static void evaluateFitness(Population pop) {

		double kolmogorovSmirnovTestResult = 0, chiSquareResult = 0, euclidianNorm = 0;

		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
			try {
				HashMap<Integer, Integer> gapLengthsMap = GapTest.countGaps(pop.getIndividual(i).toBinaryString(), 2);
				kolmogorovSmirnovTestResult = KolmogorovSmirnovTest.calculateCriticalD(gapLengthsMap, pop.getIndividual(i).getBinaryStringSize(), 2);
				chiSquareResult = frequencyTest(pop.getIndividual(i));

				// The Euclidian Norm is used as the fitness value to give fair weights
				// to outlying values of either test results
				euclidianNorm = Math.sqrt(Math.pow(kolmogorovSmirnovTestResult, 2) + Math.pow(chiSquareResult, 2));

				pop.getIndividual(i).setFitnessValue(euclidianNorm);
			} catch (IllegalArgumentException illArgExc) {
				System.out.println(illArgExc.getMessage());
			}
		}
	}

	// FIXME: add the Phi-correlation coefficient? ( =  sqrt(Chi-square / max. population or individual size))
	private static double frequencyTest(Individual individual) {

		// expectedProbability = probability of 0 or 1 ( = 50% for a binary string)
		double expectedProbability = 0.5;

		// pOf0s = probability of 0s in the observed individual ( = 0s / size of the individual)
		// pOf1s = probability of 1s in the observed individual ( = 1s / size of the individual)
		double pOf0s = 0, pOf1s = 0;

		// expected entropy = ( -p * log2(p) - (1-p) * log2(1-p) )
		double expectedEntropy = ( ( - expectedProbability * (Math.log(expectedProbability)/Math.log(2) ) )
				- ( (1 - expectedProbability) * (Math.log(1-expectedProbability)/Math.log(2)) ) );

		//
		long[] observedFrequencies = new long[]{0, 0};

		// 1st -> calculate Shannon Entropy of the individual
		observedFrequencies = PearsonsChiSquareTest.calculateObservedFrequencies(individual);
		pOf0s = observedFrequencies[0] / (double) Settings.getIndividualSize();
		pOf1s = observedFrequencies[1] / (double) Settings.getIndividualSize();

		double observedEntropy = ( ( - pOf0s * (Math.log(pOf0s)/Math.log(2) ) )
				- ( (1 - pOf1s) * (Math.log(1-pOf1s)/Math.log(2)) ) );

		// 2nd -> Calculate Chi-square of the Shannon Entropy
		double chiSquareShannonEntropyResult = PearsonsChiSquareTest.ChiSquareShannonEntropy(observedEntropy, expectedEntropy);

		// 3rd -> Test the result against the critical value for the defined alpha
		//		System.out.println("Fails to reject H0? : " + PearsonsChiSquareTest.isFailToRejectNullHypothesis(chiSquareShannonEntropyResult));
		if(PearsonsChiSquareTest.isFailToRejectNullHypothesis(chiSquareShannonEntropyResult)) {
			// fit individual according to frequency test
		}

		/* To calculate the Chi-square only over the frequencies,
		 * comment all of the above and uncomment this block
		long[] expectedFrequencies = new long[]{Settings.getIndividualSize()/2, Settings.getIndividualSize()/2};
		double chiSquareResult = PearsonsChiSquareTest.ChiSquare(PearsonsChiSquareTest.calculateObservedFrequencies(individual), expectedFrequencies);

		if(PearsonsChiSquareTest.isFailToRejectNullHypothesis(chiSquareResult)) {
			// fit individual according to frequency test
			System.out.println("Fails to reject H0? : " + PearsonsChiSquareTest.isFailToRejectNullHypothesis(chiSquareResult));
		}
		return chiSquareResult;
		 */

		return chiSquareShannonEntropyResult;
	}

	/**
	 * Crossover method.
	 * @param pop population over which the crossover operation is going to occur.
	 */
	private static void crossover(Population pop) {
		//SecureRandom secRand = new SecureRandom();
		int auxPopSize = pop.getSize(); // Store the original population size
		double numOfIndividualsToCrossover = auxPopSize * Settings.getPercentageOfIndividualsToCross();

		//		try {
		//			System.out.println("pop.getSize(): " + auxPopSize);
		//			System.out.println("Settings.getPercentageOfIndividualsToCross(): " + Settings.getPercentageOfIndividualsToCross());
		//			System.out.println("Conta deu: " + auxPopSize * Settings.getPercentageOfIndividualsToCross());
		//			System.out.println("Total de iterações a fazer: " + (auxPopSize * Settings.getPercentageOfIndividualsToCross()) * (pop.getSize() * Settings.getPercentageOfIndividualsToCross()));
		//			Thread.sleep(4000);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}

		// Loop through all individuals' applying the crossover to generate new individuals
		// Repeated crossovers are disregarded, thus n^2/2 operations are made in O((n^2))
		for(int i = 0; i < numOfIndividualsToCrossover; ++i) {
			for(int j = i+1; j < numOfIndividualsToCrossover; ++j) {
				// There is no need to choose a random crossover point, since
				// the individual only has 2 genes to work with (p, q)
				Individual ind1 = pop.newIndividual();
				Individual ind2 = pop.newIndividual();

				// Population example: I1: {p1, q1} ; I2: {p2, q2} ; I3: {p3, q3} ; ... ; In: {pn, qn}
				// Offspring after crossover of I1 and I2:
				// C1: {p1, q2}; C2: {p2, q1}
				// TODO: It is possible to generate 6 more individuals in the offspring: C3: {p1, p2}; C4: {q1, p2} C5:{q1, q2}; C6:{p2, p1}; C7:{q2, p1} C8:{q2, q1}
				// Total possible number of combination is = (number of genes between both parents)^(genes per individual)/2 ==> (4^2)/2
				// Observation.: The result is divided by 2 because an individual can't be formed with twice the same gene from a single parent, otherwise individuals that didn't go
				// through the crossover operation would be allowed.
				ind1.setP(pop.getIndividual(i).getP());
				ind1.setQ(pop.getIndividual(j).getQ());

				ind2.setP(pop.getIndividual(j).getP());
				ind2.setQ(pop.getIndividual(i).getQ());

				//				System.out.println("[LOG - DEBUG] -- crossover is alive | i = " + i + " | j = " + j);
			}
		}
	}


	/**
	 * Mutation method.
	 * @param indiv individual to possibly have a gene mutated.
	 */
	private static void mutate(Individual indiv) {
		// If mutation chance passes (meaning it will occur)...
		if (Math.random() <= Settings.getMutationRate()) {
			// ...draw a random gene to be modified (0 or 1 == P or Q), since the 3rd gene (public exponent E) won't be modified.
			//			(int) Math.floor(Math.random() * Settings.getIndividualSize()
			int randomGene = (int) Math.floor(Math.random() * (indiv.getIndividual().size()-1));

			// Mutation is performed by obtaining the next probable prime and placing it as the new value of the mutated gene
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
	 * @param pop population from which individuals are going to be selected based on their fitness values.
	 */
	public static void rankSelection(Population pop) {
		// Sorts the current population in descending order of fitness
		pop.orderByFitness();

		// Wipes the rest of the population
		while(pop.getSize() > Settings.getMaxPopulationSize()) {
			pop.kill(Settings.getMaxPopulationSize());
		}

		// After this, only the [MaxPopulationSize] individuals will be present on the population
	}
}
