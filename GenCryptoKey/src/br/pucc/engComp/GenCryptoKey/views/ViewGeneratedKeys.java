package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import br.pucc.engComp.GenCryptoKey.controller.GenCryptoKey;

@SuppressWarnings("serial")
public class ViewGeneratedKeys extends JFrame {
	/**
	 * Create the Settings frame.
	 */
	public ViewGeneratedKeys(JFrame homeFrame) {

		final JDialog viewGeneratedKeysFrame = new JDialog(homeFrame, "Generated RSA Key Pair", true);

		JPanel parentPanel = new JPanel(new BorderLayout());
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));

		JTextArea keyPairtextArea = new JTextArea(20, 70);
		keyPairtextArea.setEditable(false);
		//keyPairtextArea.setEnabled(false);
		keyPairtextArea.setLineWrap(true);
		keyPairtextArea.setText("-----BEGIN RSA PUBLIC KEY-----\n"
				+ "Public Exponent: " + GenCryptoKey.rsa.getE() + "\n"
				+ "Modulus: " + GenCryptoKey.rsa.getN() + "\n"
				+ "-----END RSA PUBLIC KEY-----\n"
				+ "-----BEGIN RSA PRIVATE KEY-----\n"
				+ "Private Exponent: " + GenCryptoKey.rsa.getD() + "\n"
				+ "Modulus: " + GenCryptoKey.rsa.getN() + "\n"
				+ "-----END RSA PRIVATE KEY-----");


		keyPairtextArea.addMouseListener(new PopUpMenuClickListener(keyPairtextArea));
		textAreaPanel.add(keyPairtextArea);

		JButton exportKeyPairToFileButton = new JButton("Export to file");
		exportKeyPairToFileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				JLabel generatedKeyMessage = new JLabel("<html><b>Exporting your keypair to a file will increase the exposure of your keys.</br> Do you wish to proceed? </html>");
				int confirmExportToFile = JOptionPane.showConfirmDialog(null, generatedKeyMessage, "Notice!",
						JOptionPane.YES_NO_OPTION, 1,
						new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png")));
				if(confirmExportToFile == JOptionPane.YES_OPTION) {
					PrintWriter exportedKeyFile = null;
					try {
						String filePath = new String("C:\\Users\\Nick\\Desktop\\Exported_CryptoKey_" + System.currentTimeMillis() + ".txt");
						exportedKeyFile = new PrintWriter(filePath);
					}catch(FileNotFoundException fnfe) {
						fnfe.printStackTrace();
					}
					exportedKeyFile.println("-----BEGIN RSA PUBLIC KEY-----");
					exportedKeyFile.println("Public Exponent: " + GenCryptoKey.rsa.getE());
					exportedKeyFile.println("Modulus: " + GenCryptoKey.rsa.getN());
					exportedKeyFile.println("-----END RSA PUBLIC KEY-----");
					exportedKeyFile.println();exportedKeyFile.println();
					exportedKeyFile.println("-----BEGIN RSA PRIVATE KEY-----");
					exportedKeyFile.println("Private Exponent: " + GenCryptoKey.rsa.getD());
					exportedKeyFile.println("Modulus: " + GenCryptoKey.rsa.getN());
					exportedKeyFile.println("-----END RSA PRIVATE KEY-----");
					exportedKeyFile.close();

					JOptionPane.showMessageDialog(null, "Your key pair has been exported to a file in your desktop.", "Keypair exported", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JButton purgeKeyPairButton = new JButton("Purge key pair");
		purgeKeyPairButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {

			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				viewGeneratedKeysFrame.dispose();
			}
		});


		buttonsPanel.add(exportKeyPairToFileButton);
		buttonsPanel.add(purgeKeyPairButton);
		buttonsPanel.add(cancelButton);


		//parentPanel.add(headerPanel, BorderLayout.NORTH);
		parentPanel.add(textAreaPanel, BorderLayout.CENTER);
		parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
		viewGeneratedKeysFrame.setContentPane(parentPanel);
		viewGeneratedKeysFrame.pack();
		viewGeneratedKeysFrame.validate();
		viewGeneratedKeysFrame.setResizable(false);
		viewGeneratedKeysFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewGeneratedKeysFrame.setLocationRelativeTo(null);
		viewGeneratedKeysFrame.setVisible(true);
	}

	class PopUpMenu extends JPopupMenu {
		JMenuItem copyOption;
		public PopUpMenu(){
			copyOption = new JMenuItem("Copy");
			add(copyOption);
		}
	}

	class PopUpMenuClickListener extends MouseAdapter {
		private JTextArea jTextArea;
		public PopUpMenuClickListener(JTextArea keyPairtextArea) {
			jTextArea = keyPairtextArea;
		}

		@Override
		public void mousePressed(MouseEvent e){
			if (e.isPopupTrigger()) {
				doPop(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e){
			String selectedText = jTextArea.getSelectedText();
			StringSelection stringSelection = new StringSelection(selectedText);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);

			if (e.isPopupTrigger()) {
				doPop(e);
			}
		}

		private void doPop(MouseEvent e){
			PopUpMenu menu = new PopUpMenu();
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}
