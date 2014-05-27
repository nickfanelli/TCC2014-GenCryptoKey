package br.pucc.engComp.genCryptoKey;

public class AG {

    /* Parâmetros do AG */
    private static final double uniformCrossoverRate = 0.5;
    private static final double mutationRate = 0.015;
    // Quanto maior o tamanho do torneio, mais indivíduos serão
    // selecionados para crossover
    // Aqui o tamanho do torneio = 10% do tamanho padrão da população
    private static final int tournamentSize = 50;
    private static final boolean elitism = true;

    /* Métodos públicos */
    
    // Evolução da população
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // "Elitismo" = guardar o invíduo mais fit da geração e passá-lo para a próxima,
        // de modo a não perder a melhor solução encontrada até o momento.
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Criação de novos indivíduos por crossover usando a população atual
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutação
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Metódo crossover
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop por todos os genes dos indivíduos pais
        for (int i = 0; i < indiv1.size(); i++) {
        	// Crossover uniforme (UX) = pontos de crossover são determinados aleatoriamente,
        	// mas o novo indivíduo possui aproximadamente metade dos genes do primeir pai 
        	// e outra metade do segundo pai
            if (Math.random() <= uniformCrossoverRate) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }

    // Método mutação
    private static void mutate(Individual indiv) {
        // Loop for todos os genes do indivíduo
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Novo gene aleatório
                byte gene = (byte) Math.round(Math.random());
                indiv.setGene(i, gene);
            }
        }
    }

    // Seleção dos invidíduos para crossover (usando-se torneio)
    // Este torneio não usa probabilidade ("1-way" = seleção aleatória)
    private static Individual tournamentSelection(Population pop) {
        // População para o torneio
        Population tournament = new Population(tournamentSize, false);
        // Seleção de um indivíduo aleatoriamente para cada posição no torneio
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Retorna indivíduo fittest (o "mais bem adaptado")
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}
