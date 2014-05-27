package br.pucc.engComp.genCryptoKey;

public class Population {

    Individual[] individuals;

    // Cria��o de uma popula��o
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // Inicializa��o da popula��o
        if (initialise) {
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    // Procura o indiv�duo mais fit
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* M�todos p�blicos */
    // Retorna tamanho da popula��o
    public int size() {
        return individuals.length;
    }

    // Guardar indiv�duo
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}