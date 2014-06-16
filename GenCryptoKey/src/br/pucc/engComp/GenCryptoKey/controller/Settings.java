package br.pucc.engComp.GenCryptoKey.controller;


public class Settings {
	/* Default parameter values */
	private static int individualSize = 192;
	private static int populationSize = 500;
	private static int numOfCrossoverPoints = 1;
	private static double mutationRate = 0.015;
	private static int maxPreservedIndividuals = 50;
	private static int numOfFitIndividualsToStop = 1;
	private static int maxGenerationsToStop = 2000;
	private static boolean scheduledKeyGeneration = false;
	private static int scheduledKeyGenerationTime = 0;
	private static boolean writeLog = false;
	
	// 92 chars
	private static String charSet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ'\"0123456789!@#$%*()-_+=`[{~^]},<.>;:/?\\";
	
	public Settings(){
		
	}

	/* Getters & setters*/
	
	public static int getPopulationSize() {
		return populationSize;
	}

	public static void setPopulationSize(int newPopulationSize) {
		populationSize = newPopulationSize;
	}

	public static int getIndividualSize() {
		return individualSize;
	}

	public static void setIndividualSize(int newIndividualSize) {
		individualSize = newIndividualSize;
	}

	public static int getNumOfCrossoverPoints() {
		return numOfCrossoverPoints;
	}

	public static void setNumOfCrossoverPoints(int newNumOfCrossoverPoints) {
		numOfCrossoverPoints = newNumOfCrossoverPoints;
	}

	public static double getMutationRate() {
		return mutationRate;
	}

	public static void setMutationRate(double newMutationRate) {
		mutationRate = newMutationRate;
	}

	public static int getMaxPreservedIndividuals() {
		return maxPreservedIndividuals;
	}

	public static void setMaxPreservedIndividuals(int newMaxPreservedIndividuals) {
		maxPreservedIndividuals = newMaxPreservedIndividuals;
	}

	public static int getNumOfFitIndividualsToStop() {
		return numOfFitIndividualsToStop;
	}

	public static void setNumOfFitIndividualsToStop(int newNumOfFitIndividualsToStop) {
		numOfFitIndividualsToStop = newNumOfFitIndividualsToStop;
	}
	
	public static int getMaxGenerationsToStop() {
		return maxGenerationsToStop;
	}

	public static void setMaxGenerationsToStop(int newMaxGenerationsToStop) {
		maxGenerationsToStop = newMaxGenerationsToStop;
	}

	public static boolean isScheduledKeyGeneration() {
		return scheduledKeyGeneration;
	}

	public static void setScheduledKeyGeneration(boolean scheduledKeyGen) {
		scheduledKeyGeneration = scheduledKeyGen;
	}

	public static int getScheduledKeyGenerationTime() {
		return scheduledKeyGenerationTime;
	}

	public static void setScheduledKeyGenerationTime(int scheduledKeyGenerationTime) {
		Settings.scheduledKeyGenerationTime = scheduledKeyGenerationTime;
	}
	
	public static boolean isWriteLog() {
		return writeLog;
	}

	public static void setWriteLog(boolean wLog) {
		writeLog = wLog;
	}
	
	public static String getCharSet() {
		return charSet;
	}
}
