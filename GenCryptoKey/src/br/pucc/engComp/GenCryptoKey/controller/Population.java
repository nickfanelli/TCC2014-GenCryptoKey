package br.pucc.engComp.GenCryptoKey.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class Population {

	ArrayList<Individual> individuals;

	// Create a population
	public Population() {
		SecureRandom secRand = new SecureRandom();
		individuals = new ArrayList<Individual>();
		for(int i = 0; i < Settings.getInitialPopulationSize(); ++i) {
			individuals.add(new Individual(secRand));
		}
	}

	// Kills individual at (index) position of the population
	public void kill(int index) {
		individuals.remove(index);
	}

	public Individual newIndividual(SecureRandom secRand) {
		Individual newInd = new Individual(secRand);
		individuals.add(newInd);
		return newInd;
	}

	public void orderByFitness() {
		Collections.sort(individuals);
	}

	/* Getters */

	// Return a specific individual
	public Individual getIndividual(int index) {
		return individuals.get(index);
	}

	// Returns the current size of the population
	public int getSize() {
		return individuals.size();
	}
}
