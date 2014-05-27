package br.pucc.engComp.genCryptoKey;

public class Individual {

    static int defaultIndividualLength = 192;
    private byte[] genes = new byte[defaultIndividualLength];
    private int fitness = 0;

    // Gera��o de um indiv�duo aleat�rio
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    /* Getters e setters */
    
    // setDefaultIndividualLength(): 
    // usado apenas caso se queira criar indiv�duos com tamanhos diferentes que o padr�o
    public static void setDefaultIndividualLength(int length) {
        defaultIndividualLength = length;
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* M�todos p�blicos */
    public int size() {
        return genes.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
