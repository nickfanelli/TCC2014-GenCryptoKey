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
		return individualSize;
	}

	public static void setIndividualLength(int newIndividualLength) {
		individualSize = newIndividualLength;
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
		return maxPreservedIndividuals;
	}

	public static void setMaxPopulationCutoff(int newMaxPopulationCutoff) {
		maxPreservedIndividuals = newMaxPopulationCutoff;
	}

	public static int getNumOfFitIndividualsForStop() {
		return numOfFitIndividualsToStop;
	}

	public static void setNumOfFitIndividualsForStop(int newNumOfFitIndividualsForStop) {
		numOfFitIndividualsToStop = newNumOfFitIndividualsForStop;
	}
	
	public static int getMaxGenerationIterations() {
		return maxGenerationsToStop;
	}

	public static void setMaxGenerationIterations(int newMaxGenerationIterations) {
		maxGenerationsToStop = newMaxGenerationIterations;
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
