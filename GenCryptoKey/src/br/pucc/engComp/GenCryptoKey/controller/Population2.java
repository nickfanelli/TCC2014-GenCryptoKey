package br.pucc.engComp.GenCryptoKey.controller;

import java.util.ArrayList;

public class Population2 {

	ArrayList<Individual2> individuals;

	// Create a population
	public Population2() {
		individuals = new ArrayList<Individual2>();
		for(int i = 0; i < Settings.getMaxPopulationSize(); ++i) {
			individuals.add(new Individual2());
		}
	}

	// Kills individual at (index) position of the population
	public void kill(int index) {
		individuals.remove(index);
	}

	public Individual2 newIndividual() {
		Individual2 nI = new Individual2();
		individuals.add(nI);
		return nI;
	}

	/* Getters */

	// Return a specific individual
	public Individual2 getIndividual(int index) {
		return individuals.get(index);
	}

	// Returns the current size of the population
	public int getSize() {
		return individuals.size();
	}
}
