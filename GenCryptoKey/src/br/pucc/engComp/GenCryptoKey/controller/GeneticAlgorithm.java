package br.pucc.engComp.GenCryptoKey.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class GeneticAlgorithm {

	// Evolve the population
	public static void evolvePopulation(Population2 pop) {

		// TODO calculate fitness of each individual in the population
		// evaluateFitness(pop)

		// Creation of new individuals through crossover using the current population
		crossover(pop);

		// Mutation
		for (int i = 0; i < pop.getSize(); i++) {
			mutate(pop.getIndividual(i));
		}

		// Selection of current fittest individuals
		rankSelection(pop);
	}

	// Fitness method
	public static void evaluateFitness(Population2 pop) {

		HashMap<BigInteger, String> nInBinaryMap = new HashMap<BigInteger, String>();
		HashMap<BigInteger, Integer> nFitnessMap = new HashMap<BigInteger, Integer>();
		BigInteger n;
		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
			// n = (p * q)
			// Each individual's 'n' is converted to Binary and stored the nInBinaryMap for future evaluation
			n = pop.getIndividual(i).getP().multiply(pop.getIndividual(i).getQ());
			nInBinaryMap.put(n, n.toString(2));
		}

		Iterator<Entry<BigInteger, String>> it = nInBinaryMap.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<BigInteger, String> entry = it.next();

			nFitnessMap.put(entry.getKey(), gapTest(entry.getValue()) + frequencyTest(entry.getValue()));
		}


	}

	private static int gapTest(String binaryString) {
		int gapFitness = 0;
		// TODO
		return gapFitness;
	}

	private static int frequencyTest(String binaryString) {
		int frequencyFitness = 0;
		// TODO
		return frequencyFitness;
	}

	// Crossover method
	private static void crossover(Population2 pop) {
		// Loop through all individuals' applying the crossover to generate new individuals
		// Repeated crossovers are disregarded, thus n^2/2 operations are made in O(n)
		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
			for(int j = i+1; j < Settings.getMaxPopulationSize(); ++j) {
				// There is no need to choose a random crossover point, since
				// the individual only has 2 genes to work with (p, q)
				Individual2 ind1 = pop.newIndividual();
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


	// Mutation method
	private static void mutate(Individual2 indiv) {
		// If mutation chance passes (meaning it will occur)...
		if (Math.random() <= Settings.getMutationRate()) {
			// ...draw a random gene to be modified (0 or 1 == P or Q), since the 3rd gene (public exponent E) won't be modified.
			int randomGene = (int) Math.floor(Math.random() * indiv.getIndividual().size()-1);

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


	// Selection method
	private static void rankSelection(Population2 pop) {

		// TODO sort by fitness value and select only the n-most fit individuals

	}
}
