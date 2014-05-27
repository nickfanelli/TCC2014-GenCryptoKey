package br.pucc.engComp.genCryptoKey;

public class GenCryptoKey {

    public static void main(String[] args) {
    
        // Solução ("chave ideal")
        FitnessCalc.setSolution("111010101010111111111111111111111001100110011111111111111111000011111111111111111111111011011111111101100111000111111111111100001111111111111111111111001001001111111111000000000111111111110000");

        // Criação da população inicial
        Population myPop = new Population(500, true);
        
        // Evolução da população até que se encontre a solução ótima ("chave ideal")
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Geração: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = AG.evolvePopulation(myPop);
        }
        System.out.println("Solução encontrada!");
        System.out.println("Número de gerações: " + generationCount);
        System.out.println("Chave:");
        System.out.println(myPop.getFittest());

    }
}