package br.pucc.engComp.GenCryptoKey.controller;

public class SettingsPOJO {
	
	private int parameterID;
	private int individualSize;
	private int populationSize;
	private int crossoverPoints;
	private int mutationRate;
	private int preservedIndividuals;
	private int fitIndividualsToStop;
	private int generationsToStop;
	private boolean scheduleKeyGeneration;
	private boolean writeLog;
	
	public SettingsPOJO (int parameterID, int individualSize, int populationSize, int crossoverPoints,
							int mutationRate, int preservedIndividuals, int fitIndividualsToStop, 
							int generationsToStop, boolean scheduleKeyGeneration, boolean writeLog){
		setParameterID(parameterID);
		setIndividualSize(individualSize);
		setPopulationSize(populationSize);
		setCrossoverPoints(crossoverPoints);
		setMutationRate(mutationRate);
		setPreservedIndividuals(preservedIndividuals);
		setFitIndividualsToStop(fitIndividualsToStop);
		setGenerationsToStop(generationsToStop);
	}
	public SettingsPOJO (){}
	
	/* Getters & Setters */
	public int getParameterID() {
		return parameterID;
	}
	
	public void setParameterID(int parameterID) {
		this.parameterID = parameterID;
	}
	
	public int getIndividualSize() {
		return individualSize;
	}
	
	public void setIndividualSize(int individualSize) {
		this.individualSize = individualSize;
	}
	
	public int getPopulationSize() {
		return populationSize;
	}
	
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	
	public int getCrossoverPoints() {
		return crossoverPoints;
	}
	
	public void setCrossoverPoints(int crossoverPoints) {
		this.crossoverPoints = crossoverPoints;
	}
	
	public int getMutationRate() {
		return mutationRate;
	}
	
	public void setMutationRate(int mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	public int getPreservedIndividuals() {
		return preservedIndividuals;
	}
	
	public void setPreservedIndividuals(int preservedIndividuals) {
		this.preservedIndividuals = preservedIndividuals;
	}
	
	public int getFitIndividualsToStop() {
		return fitIndividualsToStop;
	}
	
	public void setFitIndividualsToStop(int fitIndividualsToStop) {
		this.fitIndividualsToStop = fitIndividualsToStop;
	}
	
	public int getGenerationsToStop() {
		return generationsToStop;
	}
	
	public void setGenerationsToStop(int generationsToStop) {
		this.generationsToStop = generationsToStop;
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