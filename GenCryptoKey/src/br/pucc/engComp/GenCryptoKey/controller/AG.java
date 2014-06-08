package br.pucc.engComp.GenCryptoKey.controller;

//import java.util.ArrayList;

public class AG {
	
    /* Par�metros do AG */
    private static final int tournamentSize = 50;
    private static final int elitism = 1;

    /* M�todos p�blicos */
    
    // Evolu��o da popula��o
    public static void evolvePopulation(Population pop) {
        // Crossover
        tournamentSelection(pop);
        
        // Cria��o de novos indiv�duos por crossover usando a popula��o atual
        crossover(pop);

        // Muta��o
        for (int i = 0; i < pop.getSize(); i++) {
            mutate(pop.getIndividual(i));
        }
    }

    // Met�do crossover
    private static void crossover(Population pop) {
        // Loop por todos os genes dos indiv�duos pais
        for(int i = 0; i < tournamentSize; i++) {
        	for(int j = i+1; j < tournamentSize; ++j) {
        		int crossPoint = (int) (Math.random() * Parameters.getIndividualLength());
        		Individual ind1 = pop.newIndividual();
        		Individual ind2 = pop.newIndividual();
                
        		for (int k = 0; k < Parameters.getIndividualLength(); k++) {
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
        for(int i = elitism; i < tournamentSize; ++i) {
        	pop.kill(elitism);
        }
    }
    
    // Mutacao: se ocorrer a mutação, sortear um bit para ser alterado.

    // M�todo muta��o
    private static void mutate(Individual indiv) {
        if (Math.random() <= Parameters.getMutationRate()) {
        	int randomGene = (int) Math.floor(Math.random() * Parameters.getIndividualLength());
            // Novo gene aleat�rio
        	
        	// "2" = dominio binario para os possiveis caracteres da chave
        	// No caso de um dominio alfanumerico, trocar o "2" pelo tamanho
        	// do dominio (max. 256)
        	byte newValue = (byte) Math.floor(Math.random() * 2);
        	while(newValue == indiv.getGene(randomGene)) {
        		newValue = (byte) Math.floor(Math.random() * 2);
        	}
        	indiv.setGene(randomGene, newValue);
        }
    }

    // Sele��o dos invid�duos para crossover (usando-se torneio)
    // Este torneio n�o usa probabilidade ("1-way" = sele��o aleat�ria)
    private static void tournamentSelection(Population pop) {
        // Sele��o de um indiv�duo aleatoriamente para cada posi��o no torneio
    	pop.sort();
        while(pop.getSize() > tournamentSize) {
            pop.kill(tournamentSize);
        }
    }
}
