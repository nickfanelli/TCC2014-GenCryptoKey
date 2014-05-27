package br.pucc.engComp.genCryptoKey;

public class Individual {

    static int defaultIndividualLength = 192;
    private byte[] genes = new byte[defaultIndividualLength];
    private int fitness = 0;

    // Geração de um indivíduo aleatório
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    /* Getters e setters */
    
    // setDefaultIndividualLength(): 
    // usado apenas caso se queira criar indivíduos com tamanhos diferentes que o padrão
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

    /* Métodos públicos */
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
