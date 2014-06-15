package br.pucc.engComp.GenCryptoKey.controller;

public class SettingsPOJO {
	
	private int parameterID;
	private int individualSize;
	private int populationSize;
	private int numOfCrossoverPoints;
	private double mutationRate;
	private int maxPreservedIndividuals;
	private int numOfFitIndividualsToStop;
	private int maxGenerationsToStop;
	private boolean scheduleKeyGeneration;
	private boolean writeLog;
	
	public SettingsPOJO (int parameterID, int individualSize, int populationSize, int crossoverPoints,
							int mutationRate, int preservedIndividuals, int fitIndividualsToStop, 
							int generationsToStop, boolean scheduleKeyGeneration, boolean writeLog){
		setParameterID(parameterID);
		setIndividualSize(individualSize);
		setPopulationSize(populationSize);
		setNumOfCrossoverPoints(crossoverPoints);
		setMutationRate(mutationRate);
		setMaxPreservedIndividuals(preservedIndividuals);
		setNumOfFitIndividualsToStop(fitIndividualsToStop);
		setMaxGenerationsToStop(generationsToStop);
	}
	public SettingsPOJO (){}
	
	/* Getters & Setters */
	public int getParameterID() {
		return parameterID;
	}
	
	public void setParameterID(int newParameterID) {
		this.parameterID = newParameterID;
	}
	
	public int getIndividualSize() {
		return individualSize;
	}
	
	public void setIndividualSize(int newIndividualSize) {
		this.individualSize = newIndividualSize;
	}
	
	public int getPopulationSize() {
		return populationSize;
	}
	
	public void setPopulationSize(int newPopulationSize) {
		this.populationSize = newPopulationSize;
	}
	
	public int getNumOfCrossoverPoints() {
		return numOfCrossoverPoints;
	}
	
	public void setNumOfCrossoverPoints(int newNumOfCrossoverPoints) {
		this.numOfCrossoverPoints = newNumOfCrossoverPoints;
	}
	
	public double getMutationRate() {
		return mutationRate;
	}
	
	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	public int getMaxPreservedIndividuals() {
		return maxPreservedIndividuals;
	}
	
	public void setMaxPreservedIndividuals(int newMaxPreservedIndividuals) {
		this.maxPreservedIndividuals = newMaxPreservedIndividuals;
	}
	
	public int getNumOfFitIndividualsToStop() {
		return numOfFitIndividualsToStop;
	}
	
	public void setNumOfFitIndividualsToStop(int newNumOfFitIndividualsToStop) {
		this.numOfFitIndividualsToStop = newNumOfFitIndividualsToStop;
	}
	
	public int getMaxGenerationsToStop() {
		return maxGenerationsToStop;
	}
	
	public void setMaxGenerationsToStop(int newMaxGenerationsToStop) {
		this.maxGenerationsToStop = newMaxGenerationsToStop;
	}
	
	public boolean isScheduleKeyGeneration() {
		return scheduleKeyGeneration;
	}
	
	public void setScheduleKeyGeneration(boolean scheduleKeyGeneration) {
		this.scheduleKeyGeneration = scheduleKeyGeneration;
	}
	
	public boolean isWriteLog() {
		return writeLog;
	}
	
	public void setWriteLog(boolean writeLog) {
		this.writeLog = writeLog;
	}
}