package br.pucc.engComp.genCryptoKey;

public class AG {

    /* Par�metros do AG */
    private static final double uniformCrossoverRate = 0.5;
    private static final double mutationRate = 0.015;
    // Quanto maior o tamanho do torneio, mais indiv�duos ser�o
    // selecionados para crossover
    // Aqui o tamanho do torneio = 10% do tamanho padr�o da popula��o
    private static final int tournamentSize = 50;
    private static final boolean elitism = true;

    /* M�todos p�blicos */
    
    // Evolu��o da popula��o
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // "Elitismo" = guardar o inv�duo mais fit da gera��o e pass�-lo para a pr�xima,
        // de modo a n�o perder a melhor solu��o encontrada at� o momento.
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
        // Cria��o de novos indiv�duos por crossover usando a popula��o atual
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Muta��o
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Met�do crossover
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop por todos os genes dos indiv�duos pais
        for (int i = 0; i < indiv1.size(); i++) {
        	// Crossover uniforme (UX) = pontos de crossover s�o determinados aleatoriamente,
        	// mas o novo indiv�duo possui aproximadamente metade dos genes do primeir pai 
        	// e outra metade do segundo pai
            if (Math.random() <= uniformCrossoverRate) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }

    // M�todo muta��o
    private static void mutate(Individual indiv) {
        // Loop for todos os genes do indiv�duo
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Novo gene aleat�rio
                byte gene = (byte) Math.round(Math.random());
                indiv.setGene(i, gene);
            }
        }
    }

    // Sele��o dos invid�duos para crossover (usando-se torneio)
    // Este torneio n�o usa probabilidade ("1-way" = sele��o aleat�ria)
    private static Individual tournamentSelection(Population pop) {
        // Popula��o para o torneio
        Population tournament = new Population(tournamentSize, false);
        // Sele��o de um indiv�duo aleatoriamente para cada posi��o no torneio
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Retorna indiv�duo fittest (o "mais bem adaptado")
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}
