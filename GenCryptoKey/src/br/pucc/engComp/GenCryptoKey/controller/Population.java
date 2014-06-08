package br.pucc.engComp.GenCryptoKey.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    List<Individual> individuals;

    // Cria��o de uma popula��o
    public Population() {
        individuals = new ArrayList<Individual>();
        for(int i = 0; i < Parameters.getPopulationSize(); ++i) {
        	individuals.add(new Individual());
        }
    }
    
    /* Getters */
    public Individual getIndividual(int index) {
        return individuals.get(index);
    }
    
    // Procura o indiv�duo mais fit
    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (int i = 0; i < getSize(); i++) {
            if (fittest.getFitness() < getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
    
    // Retorna tamanho da popula��o
    public int getSize() {
        return individuals.size();
    }
    
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
}