package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import br.pucc.engComp.GenCryptoKey.controller.SettingsPOJO;
import br.pucc.engComp.GenCryptoKey.models.SettingsDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings(value = { "serial" })
public class GASettings extends JFrame {

	/**
	 * Create the Settings frame.
	 */
	public GASettings(JFrame homeFrame) {
		
		final JDialog settingsFrame = new JDialog(homeFrame, "Genetic Algorithm Settings", true);
    	
    	String[] settingsLabels = {"Individual size: ", "Population size: ", "Crossover points: ", "Mutation rate: ",
    								"Preserved individuals: ", "Fit individuals to stop: ", "Generations to stop: "};
    	final String[] defaultSettingsValues = {"192", "500", "1", "0.015", "50", "1", "2000"};
    	String[] minimumParameterValues = {"128", "50", "1", "0.005", "10", "0", "500"};
    	String[] maximumParameterValues = {"512", "5000", "10", "0.03", "500", "5", "10000"};    	
    	
    	int numLabels = settingsLabels.length;
    	
    	// This array will keep track of the value fields that can be
    	// set by the user for future updating of the database
    	final ArrayList<JSlider> settingsSliders = new ArrayList<JSlider>();
    	final ArrayList<JCheckBox> settingsCheckboxes = new ArrayList<JCheckBox>();
    	final ArrayList<JButton> settingsResetButtons = new ArrayList<JButton>();
    	JPanel parentPanel = new JPanel(new BorderLayout());
    	    	
    	// Creates and populates the panel for the grid
    	JPanel springPanel = new JPanel(new SpringLayout());
    	springPanel.setOpaque(true);
    	
    	JTextField newMutationRateField = null;
    	for (int i = 0; i < numLabels; i++) {
    		// Creating settings components ...
    		// Parameter label
    	    JLabel parameterName = new JLabel(settingsLabels[i], JLabel.TRAILING);
    	    springPanel.add(parameterName);
    	    // Parameter slider
    	    if(i == 3){
    	    	newMutationRateField = new JTextField(defaultSettingsValues[i]);
    	    	newMutationRateField.setEnabled(true);
        	    springPanel.add(newMutationRateField);
        	    //settingsSliders.add(newValueTextField);
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
    	    settingsResetButtons.add(resetParamButton);
    	}
    	
    	// This JTextField is created out here so it can be accessed later
    	// on to apply its value to the database
    	final JTextField finalMutationRateField = newMutationRateField;
    	
    	// Adding ActionListener to each individual setting Reset button
    	for(int i = 0; i < settingsResetButtons.size(); i++) {
    		final int aux = i;
    		if(i == 3) { // Skip the mutation rate index on the defaultSettingsValues list
    			settingsResetButtons.get(i).addActionListener(new ActionListener() {
    				@Override
    				public void actionPerformed(ActionEvent evt) {
    					finalMutationRateField.setText(defaultSettingsValues[aux]);
    				}
    			});
			}else if(i > 3) {
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						settingsSliders.get(aux-1).setValue(Integer.parseInt(defaultSettingsValues[aux]));
					}
				});
			}else {
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						settingsSliders.get(aux).setValue(Integer.parseInt(defaultSettingsValues[aux]));
					}
				});
			}
    	}
    	
    	JLabel scheduleLabel = new JLabel("Schedule key generation: ");
    	final JCheckBox scheduleCheckBox = new JCheckBox();
    	settingsCheckboxes.add(scheduleCheckBox);
    	JTextField scheduleTextField = new JTextField("No");
    	scheduleTextField.setEnabled(false);
    	scheduleCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(scheduleCheckBox.isSelected()) {
					final JDialog scheduleKeyFrame = new JDialog(settingsFrame, "Schedule key generation", true);
					scheduleKeyFrame.setSize(300, 100);
					JPanel scheduleKeyPanel = new JPanel();
					JPanel scheduleKeyTextPanel = new JPanel();
					scheduleKeyPanel.setLayout(new GridLayout(2, 2, 10, 0));
					scheduleKeyTextPanel.setLayout(new GridLayout(1, 2, 10, 0));
					
					JLabel scheduleKeyLabel = new JLabel("Generate keys every: ");
					JLabel minutesLabel = new JLabel("minutes.");
					JTextField minutesField = new JTextField();
					minutesField.setSize(20, 10);
					
					JButton applyScheduleKeyButton = new JButton("Apply");
					JButton cancelScheduleKeyButton = new JButton("Cancel");
					
					applyScheduleKeyButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO
						}
					});
					
					cancelScheduleKeyButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							scheduleKeyFrame.dispose();
						}
					});
					
					scheduleKeyPanel.add(scheduleKeyLabel);
					scheduleKeyTextPanel.add(minutesField);
					scheduleKeyTextPanel.add(minutesLabel);
					scheduleKeyPanel.add(scheduleKeyTextPanel);
					scheduleKeyPanel.add(applyScheduleKeyButton);
					scheduleKeyPanel.add(cancelScheduleKeyButton);
					
					scheduleKeyFrame.setContentPane(scheduleKeyPanel);
					scheduleKeyFrame.pack();
					scheduleKeyFrame.validate();
			    	scheduleKeyFrame.setResizable(false);
			    	scheduleKeyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    	scheduleKeyFrame.setLocationRelativeTo(null);
					scheduleKeyFrame.setVisible(true);
				}
			}
		});
    	
    	springPanel.add(scheduleLabel);
    	springPanel.add(scheduleCheckBox);
    	springPanel.add(scheduleTextField);
    	
    	JLabel writeLogLabel = new JLabel("Enable execution log: ");
    	JCheckBox writeLogCheckBox = new JCheckBox();
    	settingsCheckboxes.add(writeLogCheckBox);
    	JTextField writeLogTextField = new JTextField("No");
    	writeLogTextField.setEnabled(false);
    	
    	springPanel.add(writeLogLabel);
    	springPanel.add(writeLogCheckBox);
    	springPanel.add(writeLogTextField);
    	//springPanel.add(invisLabel4);
    	//springPanel.add(invisLabel5);
    	
    	// Formats the panel and creates the grid
    	SpringUtilities.makeCompactGrid(springPanel,
    	                                (numLabels + 2), 3, // lines, columns
    	                                6, 6,        // initX, initY
    	                                6, 6);       // xPad, yPad
    	    	
    	JPanel buttonsPanel = new JPanel();
    	buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
    	
    	JButton applyButton = new JButton("Apply");
    	applyButton.addActionListener(new ActionListener() {
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			// TODO Save user values to database
				// Verify that all fields are positive numbers and aren't empty
				try {
					if(finalMutationRateField != null && Double.parseDouble(finalMutationRateField.getText()) >= 0) {
						
						SettingsPOJO newSettings = new SettingsPOJO();
						
						newSettings.setIndividualSize(settingsSliders.get(0).getValue());
						newSettings.setPopulationSize(settingsSliders.get(1).getValue());
						newSettings.setNumOfCrossoverPoints(settingsSliders.get(2).getValue());
						newSettings.setMutationRate(Double.parseDouble(finalMutationRateField.getText().toString()));
						newSettings.setMaxPreservedIndividuals(settingsSliders.get(3).getValue());
						newSettings.setNumOfFitIndividualsToStop(settingsSliders.get(4).getValue());
						newSettings.setMaxGenerationsToStop(settingsSliders.get(5).getValue());
						boolean boolValue = settingsCheckboxes.get(0).isSelected();
						newSettings.setScheduleKeyGeneration(boolValue);
						/*
						if(boolValue) {
							// TODO Save the user set scheduled minutes for generation.
						}
						*/
						boolValue = settingsCheckboxes.get(1).isSelected();
						newSettings.setWriteLog(boolValue);
						
						// Save to database
						if(SettingsDAO.getInstance().newSettings(newSettings) != -1) {
							System.out.println("New settings successfully saved to the database.");
							settingsFrame.dispose();
						}
					}else {
						System.out.println("!! ==> Mutation Rate must be a positive integer <== !!");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
            }
    	});
    	JButton cancelButton = new JButton("Cancel");
    	cancelButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			settingsFrame.dispose();
            }
    	});
    	
    	JButton resetAllButton = new JButton("Reset all");
    	resetAllButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			// Recover default values and set on the text fields
				for(int i = 0; i < settingsSliders.size(); i++) {
					if(i == 3) { // Skip the mutation rate index on the defaultSettingsValues list
						settingsSliders.get(i).setValue(Integer.parseInt(defaultSettingsValues[i+1]));
					}else if(i > 3) {
						settingsSliders.get(i).setValue(Integer.parseInt(defaultSettingsValues[i+1]));
					}else {
						settingsSliders.get(i).setValue(Integer.parseInt(defaultSettingsValues[i]));
					}
				}
				finalMutationRateField.setText(defaultSettingsValues[3]);
				settingsCheckboxes.get(0).setSelected(false);
				settingsCheckboxes.get(1).setSelected(false);
            }
    	});
    	
    	buttonsPanel.add(applyButton);
    	buttonsPanel.add(cancelButton);
    	buttonsPanel.add(resetAllButton);
    	
    	//parentPanel.add(headerPanel, BorderLayout.NORTH);
    	parentPanel.add(springPanel, BorderLayout.CENTER);
    	parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
    	settingsFrame.setContentPane(parentPanel);
    	settingsFrame.pack();
    	settingsFrame.validate();
    	settingsFrame.setResizable(false);
    	settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	settingsFrame.setLocationRelativeTo(null);
    	settingsFrame.setVisible(true);
	}
}
