package br.pucc.engComp.GenCryptoKey.controller;

import java.util.ArrayList;

import br.pucc.engComp.GenCryptoKey.models.SettingsDAO;

public final class GenCryptoKey {

	private static String generatedKey = null;
	public static RSA rsa;

	public GenCryptoKey(){}

	public static void run() {
		DB.getConnection();


		/**
		 * ******************** BEGIN TESTING OF GAP TEST ******************************************************
		 */
		//		HashMap<Integer, Integer> numOfGapsPerDigit = new HashMap<Integer, Integer>();
		//		HashMap<Integer, Integer> gapLengths = new HashMap<Integer, Integer>();
		//
		//		BigInteger n = BigInteger.probablePrime(1024, new SecureRandom());
		//		String binTestIndividual = n.toString(2);
		//		System.out.println("binTestIndividual: " + binTestIndividual);
		//
		//		System.out.println();
		//
		//		char digit;
		//		int gapLength = 0, gapCount = 0;
		//		boolean foundFirstDigit = false;
		//
		//		System.out.println("=====> Binary <=====");
		//		String binIndividual = binTestIndividual;
		//		int binIndividualLength = binIndividual.length();
		//		System.out.println("individual.length(): " + binIndividualLength);
		//
		//		for(int i = 0; i <= 1; i++) {
		//			// convert i to char, for later comparison
		//			digit = Character.forDigit(i, 2);
		//			gapLength = 0;
		//			foundFirstDigit = false;
		//			for(int j = 0; j < binIndividualLength; j++) {
		//				if(binIndividual.charAt(j) == digit && !foundFirstDigit) { // Look for the first occurrence of digit 'k'
		//					foundFirstDigit = true;
		//					gapLength = 0;
		//				} else if(binIndividual.charAt(j) == digit && foundFirstDigit) { // Found the second occurrence
		//					gapCount++;
		//					Integer gapLengthKey = new Integer(gapLength);
		//
		//					Integer gapLengthValue = gapLengths.get(gapLengthKey);
		//					if(gapLengthValue == null) {
		//						gapLengths.put(gapLengthKey, 1);
		//					} else {
		//						gapLengths.put(gapLengthKey, gapLengthValue + 1);
		//					}
		//
		//					gapLength = 0;
		//				} else { // Still not equal to digit, keep counting gap length
		//					gapLength++;
		//				}
		//			}
		//			numOfGapsPerDigit.put(i, gapCount);
		//			gapCount = 0;
		//		}
		//
		//		Iterator<Entry<Integer, Integer>> itNumOfGapsPerDigit = numOfGapsPerDigit.entrySet().iterator();
		//		while(itNumOfGapsPerDigit.hasNext()) {
		//			Map.Entry<Integer, Integer> entry = itNumOfGapsPerDigit.next();
		//
		//			System.out.println("Digit " + entry.getKey() + " has " + entry.getValue() + " gaps.");
		//		}
		//
		//		System.out.println("This individual has " + gapLengths.size() + " different gap lengths.");
		//
		//		Iterator<Entry<Integer, Integer>> itGapLengths = gapLengths.entrySet().iterator();
		//
		//		DecimalFormat doubleFormat = new DecimalFormat("#.####");
		//		double countGapLengthClass = 0, gapFrequency = 0;
		//		double gapRelativeFrequency = 0; // (gapFrequency / (binIndividualLength - 2))
		//		double gapCumRelativeFrequency = 0; // Sum of all gapFrequency
		//		double gapTheoreticalFrequency = 0; // F(x)
		//		double modulusTheoreticalMinusObservedFrequency = 0; // |F(x) - Sn(x)|
		//		double maxObservedModulus = 0;
		//
		//		if(itGapLengths.hasNext()) {
		//			System.out.println("GapCumRelativeFrequency = " + gapCumRelativeFrequency);
		//			System.out.println("Gap length || Frequency || Relative Frequency || Cum. Relative Frequency ||    F(x)    || |F(x) - Sn(x)|");
		//			while(itGapLengths.hasNext()) {
		//				Map.Entry<Integer, Integer> currentEntry = itGapLengths.next();
		//
		//				countGapLengthClass++;
		//				gapFrequency = currentEntry.getValue();
		//				gapRelativeFrequency = (gapFrequency / (binIndividualLength - 2));
		//				gapCumRelativeFrequency = gapCumRelativeFrequency + gapRelativeFrequency;
		//				gapTheoreticalFrequency = 1 - Math.pow(0.9, countGapLengthClass + 1);
		//				modulusTheoreticalMinusObservedFrequency = Math.abs(gapTheoreticalFrequency - gapCumRelativeFrequency);
		//
		//				if(modulusTheoreticalMinusObservedFrequency > maxObservedModulus) {
		//					maxObservedModulus = modulusTheoreticalMinusObservedFrequency;
		//				}
		//
		//				System.out.println("     " + currentEntry.getKey() + "     ||"
		//						+ "    " + gapFrequency + "    ||"
		//						+ "      " + doubleFormat.format(gapRelativeFrequency) + "      ||"
		//						+ "          " + doubleFormat.format(gapCumRelativeFrequency) + "         ||"
		//						+ "    " + doubleFormat.format(gapTheoreticalFrequency) + "    ||"
		//						+ "  " + doubleFormat.format(modulusTheoreticalMinusObservedFrequency));
		//
		//			}
		//		}
		//		numOfGapsPerDigit.clear();
		//		gapLengths.clear();
		//
		//		System.out.println("\n\n maxObservedModulus = " + doubleFormat.format(maxObservedModulus));
		//		System.out.println("=====================\n\n\n");



		/**
		 * ******************** END TESTING OF GAP TEST ******************************************************
		 */


		/**
		 * ******************** START OLD STUB GA ALGORITHM***************************************************
		 */


		//		// Solution ("ideal key")
		//		StringBuffer solution = new StringBuffer();
		//		for(int i = 0; i < Settings.getIndividualSize(); i++) {
		//			solution.append(Settings.getCharSet().charAt(new Double(Math.floor(Math.random() * Settings.getCharSet().length())).intValue()));
		//		}
		//		System.out.println("Solution: " + solution);
		//
		//		FitnessCalc.setSolution(solution.toString());
		//		//FitnessCalc.setSolution("111010101010111111111111111111111001100110011111111111111111000011111111111111111111111011011111111101100111000111111111111100001111111111111111111111001001001111111111000000000111111111110000");
		//
		//		// Initial population
		//		Population myPop = new Population();
		//
		//		// Evolve the algorithm until the optimal solution is found ("ideal key")
		//		int generationCount = 0;
		//		while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
		//			generationCount++;
		//			System.out.println("Geracao: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
		//			//System.out.println("Indiv: " + myPop.getFittest());
		//			GA.evolvePopulation(myPop);
		//		}
		//		System.out.println("Solucao encontrada!");
		//		System.out.println("Numero de geracoes: " + generationCount);
		//		System.out.println("Chave:");
		//		System.out.println(myPop.getFittest());
		//
		//		generatedKey = myPop.getFittest().toString();

		/**
		 * ******************** START OLD STUB GA ALGORITHM***************************************************
		 */

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
		Population2 myPop = new Population2();

		// Evolve the algorithm until the optimal solution is found
		int generationCount = 1;

		// FIXME: dynamically assigns maximum generations from the Settings value -> Settings.getMaxGenerationsToStop()
		while (generationCount <= 3) {
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			System.out.println("######################################## GENERATION #: " + generationCount + " #####################################################");
			GeneticAlgorithm.evolvePopulation(myPop);
			generationCount++;
		}

		// Generate key pair using the fittest individual as base
		rsa = new RSA(myPop.getIndividual(0));
		rsa.generateKeyPair();
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