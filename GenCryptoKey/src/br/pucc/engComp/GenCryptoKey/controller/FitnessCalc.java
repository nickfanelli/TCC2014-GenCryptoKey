package br.pucc.engComp.GenCryptoKey.controller;

public class FitnessCalc {

    static byte[] solution = new byte[Parameters.getIndividualLength()];

    /* M�todos p�blicos */
    // Array de bytes para a solu��o candidata
    public static void setSolution(byte[] newSolution) {
        solution = newSolution;
    }

    // M�todo STUB para c�lculo de fitness
    // Para facilitar, transforma a solu��o string de 0s e 1s em um array de bytes
    static void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        // Loop pelo solu��o, verificando caracter a caracter
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

    // Calcula fitness de um indiv�duo comparando-o com a solu��o adotada
    static int getFitness(Individual individual) {
        int fitness = 0;
        // Compara genes do indiv�duo com a solu��o
        for (int i = 0; i < Parameters.getIndividualLength() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }
    
    // Retorna o fitness �timo
    // "Quantos bits devem ser iguais ao individuo otimo"
    static int getMaxFitness() {
        return solution.length;
    }
}
