package br.pucc.engComp.GenCryptoKey.controller;

import java.util.ArrayList;
import java.util.Calendar;

import br.pucc.engComp.GenCryptoKey.models.KeypairDAO;
import br.pucc.engComp.GenCryptoKey.models.SettingsDAO;
import br.pucc.engComp.GenCryptoKey.views.Home;

public final class GenCryptoKey {

	public static RSAKeypair rsaKeypair;
	public static KeypairPOJO generatedKeypair;

	public GenCryptoKey(){}

	public static void run() {
		DB.getConnection();

		// Check database for previously saved settings
		// If there are saved settings on DB, populate them
		ArrayList<SettingsPOJO> previousSettings = null;
		try {
			previousSettings = SettingsDAO.getAllSettingsProfiles();
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

		while (generationCount <= 1) {
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			GeneticAlgorithm.evolvePopulation(myPop);
			generationCount++;
		}

		// Generate key pair using the fittest individual as base
		rsaKeypair = new RSAKeypair(myPop.getIndividual(0));
		rsaKeypair.generateKeyPair();

		generatedKeypair = new KeypairPOJO();
		generatedKeypair.setGeneratedKeyBase64(RSAKeypair.toBase64(rsaKeypair));// FIXME: convert to PEM specification
		generatedKeypair.setPublicExponent(rsaKeypair.getE().toString());
		generatedKeypair.setPrivateExponent(rsaKeypair.getD().toString());
		generatedKeypair.setModulus(rsaKeypair.getN().toString());
		// Keypair description is set from user input off of the message dialog
		generatedKeypair.setKeypairDescription(Home.queryUserForKeypairDescription());
		generatedKeypair.setGenerationTimestamp(Calendar.getInstance());

		try {
			if(KeypairDAO.newKeypair(generatedKeypair) != -1) {
				//JOptionPane.showMessageDialog(null, "New keypair registered.", "Keypair registered", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("New keypair registered.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error inserting keypair to database: " + e.getMessage());
		}

		rsaKeypair.printExample();
	}

	public static void runGraphically() {
		// TODO Run graphical execution where user
		// can follow the whole key generation process
	}
}