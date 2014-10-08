package br.pucc.engComp.GenCryptoKey.controller;

public class SettingsPOJO {

	private int settingsID;
	private int individualSize;
	private int initialPopulationSize;
	private int numOfCrossoverPoints;
	private int numOfMutationsPerIndividual;
	private double mutationRate;
	private double percentageOfIndividualsToCross;
	private int maxPopulationSize;
	private int numOfFitIndividualsToStop;
	private int maxGenerationsToStop;
	private boolean scheduledKeyGeneration;
	private int scheduledKeyGenerationTime;
	private boolean writeLog;

	public SettingsPOJO (int parameterID, int individualSize, int initPopulationSize, int crossoverPoints,
			int mutationsPerIndividual, double mutationRate, double percentageOfIndividualsToCross,
			int populationSize,	int fitIndividualsToStop, int generationsToStop,
			boolean scheduledKeyGeneration,	int scheduledKeyGenerationTime, boolean writeLog){
		setSettingsID(parameterID);
		setIndividualSize(individualSize);
		setInitialPopulationSize(initPopulationSize);
		setNumOfCrossoverPoints(crossoverPoints);
		setNumOfMutationsPerIndividual(mutationsPerIndividual);
		setMutationRate(mutationRate);
		setPercentageOfIndividualsToCross(percentageOfIndividualsToCross);
		setMaxPopulationSize(populationSize);
		setNumOfFitIndividualsToStop(fitIndividualsToStop);
		setMaxGenerationsToStop(generationsToStop);
		setScheduledKeyGeneration(scheduledKeyGeneration);
		setScheduledKeyGenerationTime(scheduledKeyGenerationTime);
		setWriteLogActive(writeLog);
	}
	public SettingsPOJO (){}

	/* Getters & Setters */
	public int getSettingsID() {
		return settingsID;
	}

	public void setSettingsID(int newParameterID) {
		settingsID = newParameterID;
	}

	public int getIndividualSize() {
		return individualSize;
	}

	public void setIndividualSize(int newIndividualSize) {
		individualSize = newIndividualSize;
	}

	public int getInitialPopulationSize() {
		return initialPopulationSize;
	}

	public void setInitialPopulationSize(int newInitialPopulationSize) {
		initialPopulationSize = newInitialPopulationSize;
	}

	public int getNumOfCrossoverPoints() {
		return numOfCrossoverPoints;
	}

	public void setNumOfCrossoverPoints(int newNumOfCrossoverPoints) {
		numOfCrossoverPoints = newNumOfCrossoverPoints;
	}

	public int getNumOfMutationsPerIndividual() {
		return numOfMutationsPerIndividual;
	}

	public void setNumOfMutationsPerIndividual(int newNumOfMutationsPerIndividual) {
		numOfMutationsPerIndividual = newNumOfMutationsPerIndividual;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double newMutationRate) {
		mutationRate = newMutationRate;
	}

	public double getPercentageOfIndividualsToCross() {
		return percentageOfIndividualsToCross;
	}

	public void setPercentageOfIndividualsToCross(double percentageOfIndividualsToCross) {
		this.percentageOfIndividualsToCross = percentageOfIndividualsToCross;
	}

	public int getMaxPopulationSize() {
		return maxPopulationSize;
	}

	public void setMaxPopulationSize(int newMaxPreservedIndividuals) {
		maxPopulationSize = newMaxPreservedIndividuals;
	}

	public int getNumOfFitIndividualsToStop() {
		return numOfFitIndividualsToStop;
	}

	public void setNumOfFitIndividualsToStop(int newNumOfFitIndividualsToStop) {
		numOfFitIndividualsToStop = newNumOfFitIndividualsToStop;
	}

	public int getMaxGenerationsToStop() {
		return maxGenerationsToStop;
	}

	public void setMaxGenerationsToStop(int newMaxGenerationsToStop) {
		maxGenerationsToStop = newMaxGenerationsToStop;
	}

	public boolean isScheduledKeyGeneration() {
		return scheduledKeyGeneration;
	}

	public void setScheduledKeyGeneration(boolean scheduledKeyGeneration) {
		this.scheduledKeyGeneration = scheduledKeyGeneration;
	}

	public int getScheduledKeyGenerationTime() {
		return scheduledKeyGenerationTime;
	}

	public void setScheduledKeyGenerationTime(int newScheduledKeyGenerationTime) {
		scheduledKeyGenerationTime = newScheduledKeyGenerationTime;
	}

	public boolean isWriteLogActive() {
		return writeLog;
	}

	public void setWriteLogActive(boolean writeLog) {
		this.writeLog = writeLog;
	}
}