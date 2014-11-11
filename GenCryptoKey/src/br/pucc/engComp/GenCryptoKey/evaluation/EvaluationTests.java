package br.pucc.engComp.GenCryptoKey.evaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;

public final class EvaluationTests {

	private static BufferedReader inputReader, errorReader;
	private static OutputStream outputStream;
	private static PrintStream printOutputStream;

	private EvaluationTests(){} // suppressing the class constructor

	public static void Evaluate(int streamLength, int bitStreamsInFile) {
		System.out.println("Running evaluation tests...");

		try
		{
			ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Nick\\Desktop\\sts-2.1.2\\assess.exe", Integer.toString(streamLength));
			pb.redirectOutput(Redirect.INHERIT);
			pb.redirectError(Redirect.INHERIT);
			Process process = pb.start();

			String line;

			errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while((line = errorReader.readLine()) != null){
				System.out.println(line);
			}

			inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while((line = errorReader.readLine()) != null){
				System.out.println(line);
			}

			outputStream = process.getOutputStream();
			printOutputStream = new PrintStream(outputStream);
			// Path to input file
			printOutputStream.println(System.getProperty("user.home") + "\\" + streamLength + "_bit_keys"); printOutputStream.flush();
			// Execute all tests? 0: no | 1: yes
			printOutputStream.println('0'); printOutputStream.flush();
			// From 1-15, which tests to execute?
			// #4: Runs test | #14: Serial Test | #15: Linear Complexity Test
			printOutputStream.println("000100000000011"); printOutputStream.flush();
			// Adjust test parameters? 0: no | 1: 1st parameter to adjust
			printOutputStream.println('1'); printOutputStream.flush();
			// Adjusting "block length(m)" for Serial Test to 8
			printOutputStream.println('8'); printOutputStream.flush();
			// 2: 2nd parameter to adjust
			printOutputStream.println('2'); printOutputStream.flush();
			// Adjusting "block length(m)" for Linear Complexity Test to 512
			printOutputStream.println("512"); printOutputStream.flush();
			// Done adjusting parameters? 0: yes | other number: adjust parameter number
			printOutputStream.println('0'); printOutputStream.flush();
			// How many bit streams are in the input file?
			printOutputStream.println(bitStreamsInFile); printOutputStream.flush();
			// What's the input file format? 0: ASCII 0s and 1s | 1: binary (each byte in input file = 8 bits of data)
			printOutputStream.println('0'); printOutputStream.flush();


			inputReader.close();
			errorReader.close();
			printOutputStream.close();

		} catch(IOException ioe){
			System.out.println("Error during evaluation routine: " + ioe.getMessage());
		} finally {
			System.out.println("Evaluation complete!");
		}

		return;
	}

	public static boolean Runs() {
		boolean result = false;

		return result;
	}

	public static boolean Serial() {
		boolean result = false;

		return result;
	}

	public static boolean LinearComplexity() {
		boolean result = false;

		return result;
	}

}
