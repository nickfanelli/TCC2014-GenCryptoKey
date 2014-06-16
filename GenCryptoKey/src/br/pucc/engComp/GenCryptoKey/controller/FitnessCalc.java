package br.pucc.engComp.GenCryptoKey.controller;

public class FitnessCalc {

    static byte[] solution = new byte[Settings.getIndividualSize()];

    // Byte array for the candidate solution
    public static void setSolution(byte[] newSolution) {
        solution = newSolution;
    }

    // Temporary STUB method for fitness calculation (will be refined later)
    // For convenience, transforms the string solution from 0s and 1s to a byte array
    static void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        // Loop through the solution, checking character by character
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            //if (character.contains("0") || character.contains("1")) {
                solution[i] = (byte) character.charAt(0);
            //} else {
               // solution[i] = 0;
            //}
        }
    }

    // Calculates one individual's fitness comparing it to
    // the adopted (currently hard coded) optimal solution
    static int getFitness(Individual individual) {
        int fitness = 0;
        // Compares each of the individual's genes with those of the 
        // adopted optimal solution
        for (int i = 0; i < Settings.getIndividualSize() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }
    
    // Returns the highest (possible) fitness value
    // It can be read as "How many genes (or bits) must be equal to those of the optimal solution
    // so that the candidate solution can be considered fit enough to be used as a key"
    static int getMaxFitness() {
    	// Currently adopting maxFitness as the optimal solution's size,
    	// meaning a candidate solution must have all of its bits equal to those of the
    	// optimal solution to be considered fit enough to be used as a key
        return solution.length;
    }
}