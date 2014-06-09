package br.pucc.engComp.GenCryptoKey.controller;

public class GA {
	
    /* Par�metros do AG */
    private static final int elitism = 1;

    // Evolve the population
    public static void evolvePopulation(Population pop) {
        // Selection of current fittest individuals
        tournamentSelection(pop);
        
        // Creation of new individuals through crossover using the current population
        crossover(pop);

        // Mutation
        for (int i = 0; i < pop.getSize(); i++) {
            mutate(pop.getIndividual(i));
        }
    }

    // Crossover method
    private static void crossover(Population pop) {
    	// Loop through all individuals' that made it through selection
    	// applying the crossover to generate new individuals
    	// Repeated crossovers are disregarded, thus n^2/2 operations are made in O(n)
        for(int i = 0; i < Settings.getMaxPopulationCutoff(); i++) {
        	for(int j = i+1; j < Settings.getMaxPopulationCutoff(); ++j) {
        		int crossPoint = (int) (Math.random() * Settings.getIndividualLength());
        		Individual ind1 = pop.newIndividual();
        		Individual ind2 = pop.newIndividual();
        		
        		// Loop through all parent individuals' genes
        		for (int k = 0; k < Settings.getIndividualLength(); k++) {
        			if(k < crossPoint) {
        				ind1.setGene(k, pop.getIndividual(i).getGene(k));
        				ind2.setGene(k, pop.getIndividual(j).getGene(k));
        			} else {
        				ind1.setGene(k, pop.getIndividual(j).getGene(k));
        				ind2.setGene(k, pop.getIndividual(i).getGene(k));
        			}
                }
        	}
        }
        for(int i = elitism; i < Settings.getMaxPopulationCutoff(); ++i) {
        	pop.kill(elitism);
        }
    }

    // Mutation method
    private static void mutate(Individual indiv) {
    	// If mutation chance passes (meaning it will occur)...
        if (Math.random() <= Settings.getMutationRate()) {
        	// ...draw a random gene to be modified
        	int randomGene = (int) Math.floor(Math.random() * Settings.getIndividualLength());
            // New random gene
        	// "2" = binary domain range for the possible key characters
        	// In such case where an alphanumeric domain is desired, change "2"
        	// for the size of the desired domaindo dominio (max. 92 - according to charSet defined in Settings class)
        	byte newValue = (byte) Math.floor(Math.random() * 2);
        	while(newValue == indiv.getGene(randomGene)) {
        		newValue = (byte) Math.floor(Math.random() * 2);
        	}
        	indiv.setGene(randomGene, newValue);
        }
    }

    // Selection for crossover
    // The [maxPopulationCutoff] individuals are selected
    private static void tournamentSelection(Population pop) {
        // Sorts the current population in descending order of fitness
    	pop.sort();
    	// Wipes the rest of the population
        while(pop.getSize() > Settings.getMaxPopulationCutoff()) {
            pop.kill(Settings.getMaxPopulationCutoff());
        }
        // After this, only the [maxPopulationCutoff] individuals will be present on the population
    }
}