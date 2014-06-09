package br.pucc.engComp.GenCryptoKey.controller;

public final class GenCryptoKey {

	private static String generatedKey = null;
	
	public GenCryptoKey(){}
	
    public static void run() {
    	DB.getConnection();
    
        // Solution ("ideal key")
        FitnessCalc.setSolution("111010101010111111111111111111111001100110011111111111111111000011111111111111111111111011011111111101100111000111111111111100001111111111111111111111001001001111111111000000000111111111110000");

        // Initial population
        Population myPop = new Population();
        
        // Evolve the algorithm until the optimal solution is found ("ideal key")
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Geracao: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            GA.evolvePopulation(myPop);
        }
        System.out.println("Solucao encontrada!");
        System.out.println("Numero de geracoes: " + generationCount);
        System.out.println("Chave:");
        System.out.println(myPop.getFittest());

        generatedKey = myPop.getFittest().toString();
    }
    
    public static void runGraphically() {
    	// TODO Run graphical execution where user
    	// can follow the whole key generation process
    }
    
    public static String getGeneratedKey() {
    	return generatedKey;
    }
}