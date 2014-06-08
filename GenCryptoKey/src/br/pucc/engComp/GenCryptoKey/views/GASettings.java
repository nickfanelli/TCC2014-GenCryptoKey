package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings(value = { "serial" })
public class GASettings extends JFrame {

	/**
	 * Create the Settings frame.
	 */
	public GASettings() {
		
		final JFrame settingsFrame = new JFrame("Genetic Algorithm Settings");
    	
    	String[] settingsLabels = {"Individual size: ", "Population size: ", "Crossover points: ", "Mutation rate: ",
    						"Preserved individuals: ", "Fit individuals to stop: ", "Generations to stop: "};
    	String[] defaultSettingsValues = {"192", "500", "2", "0.015", "50", "1", "2000"};
    	
    	int numLabels = settingsLabels.length;
    	
    	ArrayList<JTextField> valueFields = new ArrayList<JTextField>();
    	JPanel parentPanel = new JPanel(new BorderLayout());
    	
    	JPanel headerPanel = new JPanel(new SpringLayout());
    	headerPanel.setOpaque(true);
    	JLabel invisLabel = new JLabel();
    	JTextField invisTextField = new JTextField("5");
    	JLabel defaultValuesLabel = new JLabel("Default");
    	invisLabel.setVisible(false);
    	invisTextField.setVisible(false);
    	headerPanel.add(invisLabel);
		headerPanel.add(invisTextField);
		headerPanel.add(defaultValuesLabel);
    	
    	// Formata o painel header e cria o grid
    	SpringUtilities.makeCompactGrid(headerPanel,
    	                                (1), 3, // linhas, colunas
    	                                6, 6,        // initX, initY
    	                                6, 6);       // xPad, yPad
    	
    	// Cria e popula o painel para o grid
    	JPanel springPanel = new JPanel(new SpringLayout());
    	springPanel.setOpaque(true);
    	
    	for (int i = 0; i < numLabels; i++) {
    	    JLabel l = new JLabel(settingsLabels[i], JLabel.TRAILING);
    	    springPanel.add(l);
    	    JTextField newValueTextField = new JTextField(5);
    	    newValueTextField.setText(defaultSettingsValues[i]);
    	    l.setLabelFor(newValueTextField);
    	    springPanel.add(newValueTextField);
    	    valueFields.add(newValueTextField);
    	    JTextField defaultValueTextField = new JTextField(defaultSettingsValues[i]);
    	    defaultValueTextField.setEditable(false);
    	    springPanel.add(defaultValueTextField);
    	}    	
    	
    	JLabel scheduleLabel = new JLabel("Schedule key generation: ");
    	JCheckBox scheduleCheckBox = new JCheckBox();
    	JTextField scheduleTextField = new JTextField("No");
    	scheduleTextField.setEnabled(false);
    	
    	springPanel.add(scheduleLabel);
    	springPanel.add(scheduleCheckBox);
    	springPanel.add(scheduleTextField);
    	
    	JLabel writeLogLabel = new JLabel("Enable execution log: ");
    	JCheckBox writeLogCheckBox = new JCheckBox();
    	JTextField writeLogTextField = new JTextField("No");
    	writeLogTextField.setEnabled(false);
    	
    	springPanel.add(writeLogLabel);
    	springPanel.add(writeLogCheckBox);
    	springPanel.add(writeLogTextField);
    	
    	// Formata o painel e cria o grid
    	SpringUtilities.makeCompactGrid(springPanel,
    	                                (numLabels + 2), 3, // linhas, colunas
    	                                6, 6,        // initX, initY
    	                                6, 6);       // xPad, yPad
    	    	
    	JPanel buttonsPanel = new JPanel();
    	buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
    	
    	JButton applyButton = new JButton("Apply");
    	applyButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Save user values to database
            }
    	});
    	JButton cancelButton = new JButton("Cancel");
    	cancelButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			settingsFrame.dispose();
            }
    	});
    	
    	JButton resetButton = new JButton("Reset");
    	resetButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Recover default values from database and
    			// set on the text fields
            }
    	});
    	
    	buttonsPanel.add(applyButton);
    	buttonsPanel.add(cancelButton);
    	buttonsPanel.add(resetButton);
    	
    	parentPanel.add(headerPanel, BorderLayout.NORTH);
    	parentPanel.add(springPanel, BorderLayout.CENTER);
    	parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
    	settingsFrame.setContentPane(parentPanel);
    	settingsFrame.pack();
    	settingsFrame.setResizable(false);
    	settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	settingsFrame.setLocationRelativeTo(null);
    	settingsFrame.setVisible(true);
		}
}
