package br.pucc.engComp.genCryptoKey;

public class Population {

    Individual[] individuals;

    // Criação de uma população
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // Inicialização da população
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

    // Procura o indivíduo mais fit
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Métodos públicos */
    // Retorna tamanho da população
    public int size() {
        return individuals.length;
    }

    // Guardar indivíduo
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}