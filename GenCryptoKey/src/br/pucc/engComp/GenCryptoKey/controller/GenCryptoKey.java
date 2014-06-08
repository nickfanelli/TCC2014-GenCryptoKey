package br.pucc.engComp.GenCryptoKey.controller;

public class GenCryptoKey {

    public static void main(String[] args) {
    	DB.getConnection();
    
        // Solu��o ("chave ideal")
        FitnessCalc.setSolution("111010101010111111111111111111111001100110011111111111111111000011111111111111111111111011011111111101100111000111111111111100001111111111111111111111001001001111111111000000000111111111110000");

        // Cria��o da popula��o inicial
        Population myPop = new Population();
        
        // Evolu��o da popula��o at� que se encontre a solu��o �tima ("chave ideal")
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Geracao: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            AG.evolvePopulation(myPop);
        }
        System.out.println("Solucao encontrada!");
        System.out.println("Numero de geracoes: " + generationCount);
        System.out.println("Chave:");
        System.out.println(myPop.getFittest());

    }
}