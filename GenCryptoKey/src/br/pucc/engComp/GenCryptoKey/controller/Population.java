package br.pucc.engComp.GenCryptoKey.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class Population {

	ArrayList<Individual> individuals;

	public Population(ArrayList<Individual> population) {
		if(population == null){
			createPopulation();
		} else {
			setPopulation(population);
		}
	}

	// Create a population
	private void createPopulation() {
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

	// This method is used only used when a new Individual is created by crossover, thus its data
	// doesn't need to be generated, since it will be substituted with that from the parents
	// right after its creation
	public Individual newIndividual() {
		Individual newInd = new Individual();
		individuals.add(newInd);
		return newInd;
	}

	// This method is used to generate a new population from scratch
	public Individual newIndividual(SecureRandom secRand) {
		Individual newInd = new Individual(secRand);
		individuals.add(newInd);
		return newInd;
	}

	public void orderByFitness() {
		Collections.sort(individuals);
	}

	/* Getters */

	// Return a specific Individual
	public Individual getIndividual(int index) {
		return individuals.get(index);
	}

	// Retuns the entire population as an array of Individual
	public ArrayList<Individual> getPopulation() {
		return individuals;
	}
	// Returns the current size of the population
	public int getSize() {
		return individuals.size();
	}

	/* Setter */

	// Setting a new population (e.g.: population was saved on database
	public void setPopulation(ArrayList<Individual> population) {
		individuals = population;
	}
}
