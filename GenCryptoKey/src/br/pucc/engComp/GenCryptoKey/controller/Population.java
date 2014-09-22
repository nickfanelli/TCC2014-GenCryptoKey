package br.pucc.engComp.GenCryptoKey.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    List<Individual> individuals;

    // Create a population
    public Population() {
        individuals = new ArrayList<Individual>();
        for(int i = 0; i < Settings.getInitialPopulationSize(); ++i) {
        	individuals.add(new Individual());
        }
    }
    
    // Searches for and returns the fittest individual
    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (int i = 0; i < getSize(); i++) {
            if (fittest.getFitness() < getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
    
    // Kills individual at (index) position of the population
    public void kill(int index) {
    	individuals.remove(index);
    }
    
    public Individual newIndividual() {
    	Individual nI = new Individual();
    	individuals.add(nI);
    	return nI;
    }
    
    public void sort() {
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