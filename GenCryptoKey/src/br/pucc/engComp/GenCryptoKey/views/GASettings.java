package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
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
    	String[] defaultSettingsValues = {"192", "500", "1", "0.015", "50", "1", "2000"};
    	String[] minimumParameterValues = {"128", "50", "1", "0.005", "10", "0", "500"};
    	String[] maximumParameterValues = {"512", "5000", "10", "0.03", "500", "5", "10000"};    	
    	
    	int numLabels = settingsLabels.length;
    	
    	ArrayList<JSlider> settingsSliders = new ArrayList<JSlider>();
    	JPanel parentPanel = new JPanel(new BorderLayout());
    	
    	/*
    	JPanel headerPanel = new JPanel(new SpringLayout());
    	headerPanel.setOpaque(true);
    	JLabel invisLabel = new JLabel();
    	JTextField invisTextField = new JTextField("5");
    	JLabel defaultValuesLabel = new JLabel("Default");
    	JLabel minimumValuesLabel = new JLabel("Min");
    	JLabel maximumValuesLabel = new JLabel("Max");
    	invisLabel.setVisible(false);
    	invisTextField.setVisible(false);
    	headerPanel.add(invisLabel);
		headerPanel.add(invisTextField);
		headerPanel.add(defaultValuesLabel);
		headerPanel.add(minimumValuesLabel);
		headerPanel.add(maximumValuesLabel);
    	
    	// Formats the panel and creates the grid
    	SpringUtilities.makeCompactGrid(headerPanel,
    	                                1, 5, // lines, columns
    	                                6, 6,        // initX, initY
    	                                6, 6);       // xPad, yPad
    	*/
    	
    	// Creates and populates the panel for the grid
    	JPanel springPanel = new JPanel(new SpringLayout());
    	springPanel.setOpaque(true);
    	
    	for (int i = 0; i < numLabels; i++) {
    		// Creating settings components ...
    		// Parameter label
    	    JLabel parameterName = new JLabel(settingsLabels[i], JLabel.TRAILING);
    	    springPanel.add(parameterName);
    	    // Parameter slider
    	    if(i == 3){
    	    	JTextField newValueTextField = new JTextField(defaultSettingsValues[i]);
    	    	newValueTextField.setEnabled(true);
        	    springPanel.add(newValueTextField);
        	    //valueFields.add(newValueTextField);
    	    }else{
    	    	JSlider parameterSlider = new JSlider(Integer.parseInt(minimumParameterValues[i]), 
						Integer.parseInt(maximumParameterValues[i]), 
						Integer.parseInt(defaultSettingsValues[i]));
				parameterName.setLabelFor(parameterSlider);
				springPanel.add(parameterSlider);
				settingsSliders.add(parameterSlider);
    	    }
    	    
    	    
    	    
    	    JButton resetParamButton = new JButton("Reset");
    	    springPanel.add(resetParamButton);
    	    
    	    /*
    	    // Parameter (current) value
    	    JTextField newValueTextField = new JTextField(5);
    	    newValueTextField.setText(defaultSettingsValues[i]);
    	    l.setLabelFor(newValueTextField);
    	    springPanel.add(newValueTextField);
    	    valueFields.add(newValueTextField);
    	    // Parameter default value
    	    JTextField defaultValueTextField = new JTextField(defaultSettingsValues[i]);
    	    defaultValueTextField.setEnabled(false);
    	    springPanel.add(defaultValueTextField);
    	    // Parameter minimum values
    	    JTextField minimumValueTextField = new JTextField(minimumParameterValues[i]);
    	    minimumValueTextField.setEnabled(false);
    	    springPanel.add(minimumValueTextField);
    	    // Parameter maximum values
    	    JTextField maximumValueTextField = new JTextField(maximumParameterValues[i]);
    	    maximumValueTextField.setEnabled(false);
    	    springPanel.add(maximumValueTextField);
    	    */
    	}    	
    	
    	JLabel scheduleLabel = new JLabel("Schedule key generation: ");
    	JCheckBox scheduleCheckBox = new JCheckBox();
    	JTextField scheduleTextField = new JTextField("No");
    	//JTextField invisLabel2 = new JTextField("5");
    	//JTextField invisLabel3 = new JTextField("5");
    	scheduleTextField.setEnabled(false);
    	//invisLabel2.setVisible(false);
    	//invisLabel3.setVisible(false);
    	
    	springPanel.add(scheduleLabel);
    	springPanel.add(scheduleCheckBox);
    	springPanel.add(scheduleTextField);
    	//springPanel.add(invisLabel2);
    	//springPanel.add(invisLabel3);
    	
    	JLabel writeLogLabel = new JLabel("Enable execution log: ");
    	JCheckBox writeLogCheckBox = new JCheckBox();
    	JTextField writeLogTextField = new JTextField("No");
    	//JTextField invisLabel4 = new JTextField("5");
    	//JTextField invisLabel5 = new JTextField("5");
    	writeLogTextField.setEnabled(false);
    	//invisLabel4.setVisible(false);
    	//invisLabel5.setVisible(false);
    	
    	springPanel.add(writeLogLabel);
    	springPanel.add(writeLogCheckBox);
    	springPanel.add(writeLogTextField);
    	//springPanel.add(invisLabel4);
    	//springPanel.add(invisLabel5);
    	
    	// Formates the panel and creates the grid
    	SpringUtilities.makeCompactGrid(springPanel,
    	                                (numLabels + 2), 3, // lines, columns
    	                                6, 6,        // initX, initY
    	                                6, 6);       // xPad, yPad
    	    	
    	JPanel buttonsPanel = new JPanel();
    	buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
    	
    	JButton applyButton = new JButton("Apply");
    	applyButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Save user values to database
				// Verify that all fields are numbers and aren't empty
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
    	
    	//parentPanel.add(headerPanel, BorderLayout.NORTH);
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
