package br.pucc.engComp.GenCryptoKey.controller;

public class Individual implements Comparable<Individual> {

    private byte[] genes = new byte[Settings.getIndividualLength()]; // default = 192
    private int fitness = -1;

    // Generate random individual
    public Individual() {
        for (int i = 0; i < Settings.getIndividualLength(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }
    
    /* Getters & Setters */
    
    public byte getGene(int index) {
        return genes[index];
    }
    
    public byte[] getGenes() {
    	return genes;
    }

    public void setGene(int index, byte value) {
    	genes[index] = value;
        fitness = -1;
    }

    public int getFitness() {
        if (fitness == -1) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < Settings.getIndividualLength(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }

	@Override
	public int compareTo(Individual target) {
		return ((Individual) target).getFitness() - this.getFitness();
	}
}
