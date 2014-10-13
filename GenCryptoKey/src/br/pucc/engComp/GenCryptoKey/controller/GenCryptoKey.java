package br.pucc.engComp.GenCryptoKey.controller;

import java.util.ArrayList;

import br.pucc.engComp.GenCryptoKey.models.SettingsDAO;

public final class GenCryptoKey {

	private static String generatedKey = null;
	public static RSA rsa;

	public GenCryptoKey(){}

	public static void run() {
		DB.getConnection();

		// Check database for previously saved settings
		// If there are saved settings on DB, populate them
		ArrayList<SettingsPOJO> previousSettings = null;
		try {
			previousSettings = SettingsDAO.getInstance().getSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(previousSettings != null && !previousSettings.isEmpty()) { // Use the last saved settings --> (previousSettings.size()-1)
			Settings.setIndividualSize(previousSettings.get(previousSettings.size()-1).getIndividualSize());
			Settings.setInitialPopulationSize(previousSettings.get(previousSettings.size()-1).getInitialPopulationSize());
			Settings.setNumOfCrossoverPoints(previousSettings.get(previousSettings.size()-1).getNumOfCrossoverPoints());
			Settings.setNumOfMutationsPerIndividual(previousSettings.get(previousSettings.size()-1).getNumOfMutationsPerIndividual());
			Settings.setMutationRate(previousSettings.get(previousSettings.size()-1).getMutationRate());
			Settings.setPercentageOfIndividualsToCross(previousSettings.get(previousSettings.size()-1).getPercentageOfIndividualsToCross());
			Settings.setMaxPopulationSize(previousSettings.get(previousSettings.size()-1).getMaxPopulationSize());
			Settings.setNumOfFitIndividualsToStop(previousSettings.get(previousSettings.size()-1).getNumOfFitIndividualsToStop());
			Settings.setMaxGenerationsToStop(previousSettings.get(previousSettings.size()-1).getMaxGenerationsToStop());
			Settings.setScheduledKeyGeneration(previousSettings.get(previousSettings.size()-1).isScheduledKeyGeneration());
			Settings.setScheduledKeyGenerationTime(previousSettings.get(previousSettings.size()-1).getScheduledKeyGenerationTime());
			Settings.setWriteLogActive(previousSettings.get(previousSettings.size()-1).isWriteLogActive());
		}

		System.out.println("individualSize = " + Settings.getIndividualSize());
		System.out.println("initialPopulationSize = " + Settings.getInitialPopulationSize());
		System.out.println("numOfCrossoverPoints = " + Settings.getNumOfCrossoverPoints());
		System.out.println("numOfMutationsPerIndividual = " + Settings.getNumOfMutationsPerIndividual());
		System.out.println("mutationRate = " + Settings.getMutationRate());
		System.out.println("percentageOfIndividualsToCross = " + Settings.getPercentageOfIndividualsToCross());
		System.out.println("maxPopulationSize = " + Settings.getMaxPopulationSize());
		System.out.println("numOfFitIndividualsToStop = " + Settings.getNumOfFitIndividualsToStop());
		System.out.println("maxGenerationsToStop = " + Settings.getMaxGenerationsToStop());
		//		System.out.println("scheduledKeyGeneration = " + Settings.isScheduledKeyGeneration());
		//		System.out.println("scheduledKeyGenerationTime = " + Settings.getScheduledKeyGenerationTime());
		System.out.println("writeLog = " + Settings.isWriteLogActive());


		// Initial population
		Population myPop = new Population();

		// Evolve the algorithm until the optimal solution is found
		int generationCount = 1;

		GeneticAlgorithm.evaluateFitness(myPop);
		GeneticAlgorithm.rankSelection(myPop);
		// FIXME: dynamically assign maximum generations from the Settings value -> Settings.getMaxGenerationsToStop()

		while (generationCount <= 3) {
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			GeneticAlgorithm.evolvePopulation(myPop);
			generationCount++;
		}

		// Generate key pair using the fittest individual as base
		rsa = new RSA(myPop.getIndividual(0));
		rsa.generateKeyPair();
		// TODO: add the generated keypair to the database
		rsa.printExample();
	}

	public static void runGraphically() {
		// TODO Run graphical execution where user
		// can follow the whole key generation process
	}

	public static String getGeneratedKey() {
		return generatedKey;
	}
}