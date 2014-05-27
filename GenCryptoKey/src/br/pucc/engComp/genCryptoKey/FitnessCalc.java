package br.pucc.engComp.genCryptoKey;

public class FitnessCalc {

    static byte[] solution = new byte[192];

    /* Métodos públicos */
    // Array de bytes para a solução candidata
    public static void setSolution(byte[] newSolution) {
        solution = newSolution;
    }

    // Método STUB para cálculo de fitness
    // Para facilitar, transforma a solução string de 0s e 1s em um array de bytes
    static void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        // Loop pelo solução, verificando caracter a caracter
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

    // Calcula fitness de um indivíduo comparando-o com a solução adotada
    static int getFitness(Individual individual) {
        int fitness = 0;
        // Compara genes do indivíduo com a solução
        for (int i = 0; i < individual.size() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }
    
    // Retorna o fitness ótimo
    static int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }
}
