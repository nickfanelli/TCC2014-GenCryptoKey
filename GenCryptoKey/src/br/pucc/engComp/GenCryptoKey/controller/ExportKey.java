package br.pucc.engComp.GenCryptoKey.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.pucc.engComp.GenCryptoKey.views.Home;

public class ExportKey {

	public static boolean exportToFileAsPlainText(KeypairPOJO keypairToExport) {
		boolean exportResult = false;

		JLabel generatedKeyMessage = new JLabel("<html><b>Exporting your keypair to a file will increase the exposure of your keys.</br> Do you wish to proceed? </html>");
		int confirmExportToFile = JOptionPane.showConfirmDialog(null, generatedKeyMessage, "Notice!",
				JOptionPane.YES_NO_OPTION, 1,
				new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png")));

		if(confirmExportToFile == JOptionPane.YES_OPTION) {

			/** File chooser to set the directory for key exporting */
			JFileChooser exportKeyDirectoryChooser = new JFileChooser();
			exportKeyDirectoryChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
			String defaultFilename = "Exported_CryptoKey_" + System.currentTimeMillis() + ".txt";
			exportKeyDirectoryChooser.setSelectedFile(new File(defaultFilename));
			exportKeyDirectoryChooser.setDialogTitle("Export");
			exportKeyDirectoryChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			exportKeyDirectoryChooser.setAcceptAllFileFilterUsed(false);

			if (exportKeyDirectoryChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				PrintWriter exportedKeyFile = null;
				try {
					exportedKeyFile = new PrintWriter(exportKeyDirectoryChooser.getSelectedFile());

					exportedKeyFile.println("-----BEGIN RSA PUBLIC KEY-----");
					exportedKeyFile.println("Public Exponent: " + keypairToExport.getPublicExponent());
					exportedKeyFile.println("Modulus: " + keypairToExport.getModulus());
					exportedKeyFile.println("-----END RSA PUBLIC KEY-----");
					exportedKeyFile.println();exportedKeyFile.println();
					exportedKeyFile.println("-----BEGIN RSA PRIVATE KEY-----");
					exportedKeyFile.println("Private Exponent: " + keypairToExport.getPrivateExponent());
					exportedKeyFile.println("Modulus: " + keypairToExport.getModulus());
					exportedKeyFile.println("-----END RSA PRIVATE KEY-----");
					exportedKeyFile.close();

					exportResult = true;
					JOptionPane.showMessageDialog(null, "Your key pair has been exported.", "Keypair exported", JOptionPane.INFORMATION_MESSAGE);
				}catch(FileNotFoundException fnfe) {
					exportResult = false;
					fnfe.printStackTrace();
				}

			}
		}
		return exportResult;
	}

	protected static void exportBinaryIndividualForEvaluation(String binaryIndividual) {
		PrintWriter pWriter;
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home") + "\\" + binaryIndividual.length() + "_bit_keys", true)));
			//			pWriter = new PrintWriter(new java.io.File(System.getProperty("user.home") + "\\" + binaryIndividual.length() + "_" + Calendar.getInstance().getTimeInMillis()));
			pWriter.println(binaryIndividual);
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

