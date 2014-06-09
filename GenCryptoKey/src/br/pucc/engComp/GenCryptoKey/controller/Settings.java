package br.pucc.engComp.GenCryptoKey.controller;


public class Settings {
	/* Default parameter values */
	private static int populationSize = 500;
	private static int individualLength = 192;
	private static int numOfCrossoverPoints = 1;
	private static double mutationRate = 0.015;
	private static int maxPopulationCutoff = 50;
	private static int maxGenerationIterations = 2000;
	private static int numOfFitIndividualsForStop = 1;
	private static boolean scheduledKeyGeneration = false;
	private static boolean writeLog = false;
	
	String charSet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ'\"0123456789!@#$%*()-_+=`[{~^]},<.>;:/?";
	
	public Settings(){
	}

	/* Getters & setters*/
	
	public static int getPopulationSize() {
		return populationSize;
	}

	public static void setPopulationSize(int newPopulationSize) {
		populationSize = newPopulationSize;
	}

	public static int getIndividualLength() {
		return individualLength;
	}

	public static void setIndividualLength(int newIndividualLength) {
		individualLength = newIndividualLength;
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

	public static int getMaxPopulationCutoff() {
		return maxPopulationCutoff;
	}

	public static void setMaxPopulationCutoff(int newMaxPopulationCutoff) {
		maxPopulationCutoff = newMaxPopulationCutoff;
	}

	public static int getMaxGenerationIterations() {
		return maxGenerationIterations;
	}

	public static void setMaxGenerationIterations(int newMaxGenerationIterations) {
		maxGenerationIterations = newMaxGenerationIterations;
	}

	public static int getNumOfFitIndividualsForStop() {
		return numOfFitIndividualsForStop;
	}

	public static void setNumOfFitIndividualsForStop(int newNumOfFitIndividualsForStop) {
		numOfFitIndividualsForStop = newNumOfFitIndividualsForStop;
	}

	public static boolean isScheduledKeyGeneration() {
		return scheduledKeyGeneration;
	}

	public static void setScheduledKeyGeneration(boolean scheduledKeyGen) {
		scheduledKeyGeneration = scheduledKeyGen;
	}

	public static boolean isWriteLog() {
		return writeLog;
	}

	public static void setWriteLog(boolean wLog) {
		writeLog = wLog;
	}
}
