package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import br.pucc.engComp.GenCryptoKey.controller.ExportKey;
import br.pucc.engComp.GenCryptoKey.controller.KeypairPOJO;
import br.pucc.engComp.GenCryptoKey.models.KeypairDAO;

@SuppressWarnings("serial")
public class ViewKeys extends JFrame {

	private static JTable keypairsTable;
	private static ArrayList<KeypairPOJO> keypairs = new ArrayList<KeypairPOJO>();

	/**
	 * Create the ViewKeys frame.
	 */
	public ViewKeys(JFrame homeFrame, String keypairDescription) {

		final JDialog viewGeneratedKeysFrame = new JDialog(homeFrame, "Generated RSA Key Pair", true);

		KeypairPOJO keypair = null;
		final KeypairPOJO finalKeypair;

		JPanel parentPanel = new JPanel(new BorderLayout());
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));

		JTextArea keyPairtextArea = new JTextArea(20, 70);
		keyPairtextArea.setEditable(false);
		//keyPairtextArea.setEnabled(false);
		keyPairtextArea.setLineWrap(true);

		ArrayList<KeypairPOJO> registeredKeypairs = null;
		try {
			registeredKeypairs = KeypairDAO.getKeypairs();
		} catch(Exception e) {
			e.printStackTrace();
		}

		if (keypairDescription == null) {
			if (registeredKeypairs.isEmpty()) {
				JOptionPane.showMessageDialog(null, "There are no keypairs registered.", "Notice", JOptionPane.INFORMATION_MESSAGE);
			} else {
				keypair = registeredKeypairs.get(registeredKeypairs.size() - 1);
				keyPairtextArea.setText("-----BEGIN RSA PUBLIC KEY-----\n"
						+ "Public Exponent: " + keypair.getPublicExponent() + "\n"
						+ "Modulus: " + keypair.getModulus() + "\n"
						+ "-----END RSA PUBLIC KEY-----\n"
						+ "-----BEGIN RSA PRIVATE KEY-----\n"
						+ "Private Exponent: " + keypair.getPrivateExponent() + "\n"
						+ "Modulus: " + keypair.getModulus() + "\n"
						+ "-----END RSA PRIVATE KEY-----");
			}
		} else {
			try {
				keypair = KeypairDAO.getKeypairByDescription(keypairDescription);

				keyPairtextArea.setText("-----BEGIN RSA PUBLIC KEY-----\n"
						+ "Public Exponent: " + keypair.getPublicExponent() + "\n"
						+ "Modulus: " + keypair.getModulus() + "\n"
						+ "-----END RSA PUBLIC KEY-----\n"
						+ "-----BEGIN RSA PRIVATE KEY-----\n"
						+ "Private Exponent: " + keypair.getPrivateExponent() + "\n"
						+ "Modulus: " + keypair.getModulus() + "\n"
						+ "-----END RSA PRIVATE KEY-----");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		finalKeypair = keypair;
		keyPairtextArea.addMouseListener(new PopUpMenuClickListener(keyPairtextArea));
		textAreaPanel.add(keyPairtextArea);

		JButton exportKeyPairToFileButton = new JButton("Export to file");
		exportKeyPairToFileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				ExportKey.exportToFileAsPlainText(finalKeypair, viewGeneratedKeysFrame);
			}
		});

		JButton purgeKeyPairButton = new JButton("Purge key pair");
		purgeKeyPairButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					KeypairDAO.deleteKeypair(finalKeypair);
					viewGeneratedKeysFrame.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
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
			copyOption.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/copy24px.png")));
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

	public static void getGeneratedKeypairsList() {

		final JFrame keypairsListFrame = new JFrame("Registered keypairs");
		keypairsListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		DefaultTableModel model;
		Vector<String> columns = new Vector<String>();
		columns.add("Keypair description"); columns.add("Generated date");

		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col)
			{ return false; }
		};

		model.setColumnCount(columns.size());
		model.setColumnIdentifiers(columns);

		keypairsTable = new JTable(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (isRowSelected(row) && isColumnSelected(column)) {
					((JComponent) c).setBorder(new LineBorder(Color.blue));
				}
				return c;
			}
		};

		keypairsTable.setPreferredScrollableViewportSize(keypairsTable.getPreferredSize());
		JScrollPane scrollPaneTable = new JScrollPane(keypairsTable,  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		keypairsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				int row = keypairsTable.getSelectedRow();
				keypairs.get(row);
			}
		});

		try {
			updateRegisteredKeypairsTable();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured trying to query the database.", "Database error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error loading registered keypairs from database: " + e.getMessage());
		}

		if(keypairs.isEmpty()) { // No keypairs are stored in the database at this given moment
			JOptionPane.showMessageDialog(null, "There are no keypairs registered.", "Notice", JOptionPane.INFORMATION_MESSAGE);
		}

		JButton showKeypairButton = new JButton("Show keypair");
		showKeypairButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new ViewKeys(null, keypairs.get(keypairsTable.getSelectedRow()).getKeypairDescription());
			}
		});

		JButton exportKeypairButton = new JButton("Export keypair");
		exportKeypairButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ExportKey.exportToFileAsPlainText(keypairs.get(keypairsTable.getSelectedRow()), null);
			}
		});

		JButton purgeKeypairButton = new JButton("Purge keypair");
		purgeKeypairButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(keypairsTable.getSelectedRow() != -1){
					int sel = keypairsTable.getSelectedRow();
					try {
						KeypairDAO.deleteKeypair(keypairs.get(sel));
					} catch(Exception e) {
						e.printStackTrace();
					}
					updateRegisteredKeypairsTable();
				} else {
					JOptionPane.showMessageDialog(null, "Please select the line keypair you wish to purge.", "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				keypairsListFrame.dispose();
			}
		});

		JPanel keypairsPanel = new JPanel();
		keypairsPanel.setLayout(new BorderLayout());
		keypairsPanel.add(BorderLayout.CENTER, scrollPaneTable);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 1));
		p2.add(new Label());

		JPanel p3 = new JPanel();
		// Buttons separated by a JLabel space
		p3.setLayout(new GridLayout(1, 9));
		p3.add(new Label());
		p3.add(showKeypairButton); p3.add(new Label());
		p3.add(exportKeypairButton); p3.add(new Label());
		p3.add(purgeKeypairButton); p3.add(new Label());
		p3.add(cancelButton);
		p3.add(new Label());

		p2.add(p3);
		keypairsPanel.add(BorderLayout.SOUTH, p2);

		keypairsListFrame.getContentPane().add(keypairsPanel, BorderLayout.CENTER);
		keypairsListFrame.pack();
		keypairsListFrame.setResizable(false);
		keypairsListFrame.setLocationRelativeTo(null);
		keypairsListFrame.setSize(900, 300);
		keypairsListFrame.setVisible(true);
	}

	private static void updateRegisteredKeypairsTable() {
		DefaultTableModel defaultTableModel = (DefaultTableModel) keypairsTable.getModel();
		while(defaultTableModel.getRowCount() != 0) {
			defaultTableModel.removeRow(0);
		}
		keypairs.clear();

		ArrayList<KeypairPOJO> registeredKeypairs = null;
		try {
			registeredKeypairs = KeypairDAO.getKeypairs();
		} catch(Exception e) {
			e.printStackTrace();
		}

		if(registeredKeypairs != null) {
			for(KeypairPOJO keypair : registeredKeypairs) {
				defaultTableModel.addRow(new String[]{keypair.getKeypairDescription(), keypair.getGenerationTimestampAsString()});
				keypairs.add(keypair);
			}
		}
		keypairsTable.setModel(defaultTableModel);
	}
}
