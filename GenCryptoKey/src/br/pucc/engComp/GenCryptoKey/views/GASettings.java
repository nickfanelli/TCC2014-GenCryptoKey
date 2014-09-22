package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.pucc.engComp.GenCryptoKey.controller.Settings;
import br.pucc.engComp.GenCryptoKey.controller.SettingsPOJO;
import br.pucc.engComp.GenCryptoKey.models.SettingsDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

@SuppressWarnings(value = { "serial" })
public class GASettings extends JFrame {
	/**
	 * Create the Settings frame.
	 */
	public GASettings(JFrame homeFrame) {

		final JDialog settingsFrame = new JDialog(homeFrame, "Genetic Algorithm Settings", true);
		final SettingsPOJO newSettings = new SettingsPOJO();

		// TODO Retrieve all settings values from Settings class
		String[] settingsLabels = {"Key size: ", "Initial population size: ", "Crossover points: ", "Number of mutations per individual:", "Mutation rate: ",
				"Number of individuals to live: ", "Fit individuals to stop: ", "Maximum number of generations: "};

		final String[] defaultSettingsValues = {"1024",  "500",  "1",  "1", "0.015",  "50", "1",  "2000"};
		String[] minimumParameterValues      = {"128",    "50",  "1",  "1", "0.005",  "10", "0",   "500"};
		String[] maximumParameterValues      = {"3072", "5000", "10", "12",  "0.03", "500", "5", "10000"};

		int numLabels = settingsLabels.length;

		// This array will keep track of the value fields that can be
		// set by the user for future updating of the database
		final ArrayList<JSlider> settingsSliders = new ArrayList<JSlider>();
		final ArrayList<JCheckBox> settingsCheckboxes = new ArrayList<JCheckBox>();
		final ArrayList<JTextField> settingsValueDisplay = new ArrayList<JTextField>();
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
			if(i == 4){
				newMutationRateField = new JTextField(defaultSettingsValues[i]);
				parameterName.setLabelFor(newMutationRateField);
				newMutationRateField.setEnabled(true);
				springPanel.add(newMutationRateField);
				//settingsSliders.add(newValueTextField);

				final JTextField valueDisplayTextField = new JTextField(newMutationRateField.getText());
				valueDisplayTextField.setName(parameterName.getName());
				valueDisplayTextField.setEnabled(false);
				springPanel.add(valueDisplayTextField);
				settingsValueDisplay.add(valueDisplayTextField);

				final JTextField mutationRateFieldAux = newMutationRateField;
				newMutationRateField.addKeyListener(new KeyListener() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						valueDisplayTextField.setText(mutationRateFieldAux.getText());
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// Auto-generated method stub

					}

					@Override
					public void keyPressed(KeyEvent arg0) {
						// Auto-generated method stub

					}
				});

			}else{
				JSlider parameterSlider = new JSlider(Integer.parseInt(minimumParameterValues[i]),
						Integer.parseInt(maximumParameterValues[i]),
						Integer.parseInt(defaultSettingsValues[i]));

				parameterName.setLabelFor(parameterSlider);
				springPanel.add(parameterSlider);
				settingsSliders.add(parameterSlider);

				// This 'display' JTextField has to be final so it can be accessed
				// on the ChangeListener class defined below
				final JTextField valueDisplayTextField = new JTextField(Integer.toString(parameterSlider.getValue()));
				valueDisplayTextField.setEnabled(false);
				springPanel.add(valueDisplayTextField);
				settingsValueDisplay.add(valueDisplayTextField);

				parameterSlider.addChangeListener(new ChangeListener () {
					// Listens to JSlider changes and sets its value to the
					// corresponding JTextField (display)
					@Override
					public void stateChanged(ChangeEvent evt) {
						JSlider source = (JSlider)evt.getSource();
						int value = source.getValue();
						valueDisplayTextField.setText(Integer.toString(value));
					}
				});
			}
			JButton resetParamButton = new JButton("Reset");
			springPanel.add(resetParamButton);
			settingsResetButtons.add(resetParamButton);
		}

		// This JTextField is created out here (and is final) so it can be accessed later on
		// to apply its value to the database
		final JTextField finalMutationRateField = newMutationRateField;

		// Adding ActionListener to each individual setting Reset button
		for(int i = 0; i < settingsResetButtons.size(); i++) {
			final int aux = i;
			if(i == 4) { // Skip the mutation rate index on the defaultSettingsValues list
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						finalMutationRateField.setText(defaultSettingsValues[aux]);
					}
				});
			}else if(i > 4) {
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

		// TODO Schedule Key Generation: not going to be implemented in the 1st version
		//		JLabel scheduleLabel = new JLabel("Schedule key generation: ");
		//		final JCheckBox scheduleCheckBox = new JCheckBox();
		//		scheduleLabel.setLabelFor(scheduleCheckBox);
		//		settingsCheckboxes.add(scheduleCheckBox);
		//		JTextField scheduleTextField = new JTextField("No");
		//		scheduleTextField.setEnabled(false);
		//		JButton resetScheduleButton = new JButton("Reset");
		//		resetScheduleButton.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent evt) {
		//				scheduleCheckBox.setSelected(false);
		//			}
		//		});
		//
		//		final JTextField minutesField = new JTextField();
		//		scheduleCheckBox.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent evt) {
		//				if(scheduleCheckBox.isSelected()) {
		//					final JDialog scheduleKeyFrame = new JDialog(settingsFrame, "Schedule key generation", true);
		//					scheduleKeyFrame.setSize(300, 100);
		//					JPanel scheduleKeyPanel = new JPanel();
		//					JPanel scheduleKeyTextPanel = new JPanel();
		//					scheduleKeyPanel.setLayout(new GridLayout(2, 2, 10, 0));
		//					scheduleKeyTextPanel.setLayout(new GridLayout(1, 2, 10, 0));
		//					JLabel scheduleKeyLabel = new JLabel("Generate keys every: ");
		//					JLabel minutesLabel = new JLabel("minutes.");
		//
		//					minutesField.setSize(20, 10);
		//
		//					JButton applyScheduleKeyButton = new JButton("Apply");
		//					JButton cancelScheduleKeyButton = new JButton("Cancel");
		//
		//					applyScheduleKeyButton.addActionListener(new ActionListener() {
		//						@Override
		//						public void actionPerformed(ActionEvent evt) {
		//							newSettings.setScheduledKeyGenerationTime(Integer.parseInt(minutesField.getText()));
		//							scheduleKeyFrame.dispose();
		//						}
		//					});
		//
		//					cancelScheduleKeyButton.addActionListener(new ActionListener() {
		//						@Override
		//						public void actionPerformed(ActionEvent evt) {
		//							scheduleKeyFrame.dispose();
		//						}
		//					});
		//
		//					scheduleKeyPanel.add(scheduleKeyLabel);
		//					scheduleKeyTextPanel.add(minutesField);
		//					scheduleKeyTextPanel.add(minutesLabel);
		//					scheduleKeyPanel.add(scheduleKeyTextPanel);
		//					scheduleKeyPanel.add(applyScheduleKeyButton);
		//					scheduleKeyPanel.add(cancelScheduleKeyButton);
		//
		//					scheduleKeyFrame.setContentPane(scheduleKeyPanel);
		//					scheduleKeyFrame.pack();
		//					scheduleKeyFrame.validate();
		//					scheduleKeyFrame.setResizable(false);
		//					scheduleKeyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//					scheduleKeyFrame.setLocationRelativeTo(null);
		//					scheduleKeyFrame.setVisible(true);
		//				}else {
		//					newSettings.setScheduledKeyGenerationTime(0);
		//				}
		//			}
		//		});
		//
		//		springPanel.add(scheduleLabel);
		//		springPanel.add(scheduleCheckBox);
		//		springPanel.add(scheduleTextField);
		//		springPanel.add(resetScheduleButton);

		JLabel writeLogLabel = new JLabel("Enable execution log: ");
		final JCheckBox writeLogCheckBox = new JCheckBox();
		writeLogLabel.setLabelFor(writeLogCheckBox);
		settingsCheckboxes.add(writeLogCheckBox);
		final JTextField writeLogTextField = new JTextField("No");
		writeLogTextField.setEnabled(false);

		writeLogCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(writeLogCheckBox.isSelected()) {
					writeLogTextField.setText("Yes");
				} else {
					writeLogTextField.setText("No");
				}
			}
		});

		JButton resetWriteLogButton = new JButton("Reset");
		resetWriteLogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				writeLogCheckBox.setSelected(false);
			}
		});

		springPanel.add(writeLogLabel);
		springPanel.add(writeLogCheckBox);
		springPanel.add(writeLogTextField);
		springPanel.add(resetWriteLogButton);

		// Formats the panel and creates the grid
		// '+1' row is necessary for the writeLog checkbox.
		// Schedule Key Generation will add 1 when implemented
		SpringUtilities.makeCompactGrid(springPanel,
				(numLabels + 1), 4, // lines, columns
				6, 6,        // initX, initY
				6, 6);       // xPad, yPad

		// If there are saved settings on DB, populate them
		ArrayList<SettingsPOJO> previousSettings = null;
		try {
			previousSettings = SettingsDAO.getInstance().getSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(previousSettings != null && !previousSettings.isEmpty()) { // Use the last saved settings --> (previousSettings.size()-1)
			settingsSliders.get(0).setValue(previousSettings.get(previousSettings.size()-1).getIndividualSize());
			settingsSliders.get(1).setValue(previousSettings.get(previousSettings.size()-1).getPopulationSize());
			settingsSliders.get(2).setValue(previousSettings.get(previousSettings.size()-1).getNumOfCrossoverPoints());
			settingsSliders.get(3).setValue(previousSettings.get(previousSettings.size()-1).getNumOfMutationsPerIndividual());
			finalMutationRateField.setText(Double.toString(previousSettings.get(previousSettings.size()-1).getMutationRate()));
			settingsSliders.get(4).setValue(previousSettings.get(previousSettings.size()-1).getMaxPreservedIndividuals());
			settingsSliders.get(5).setValue(previousSettings.get(previousSettings.size()-1).getNumOfFitIndividualsToStop());
			settingsSliders.get(6).setValue(previousSettings.get(previousSettings.size()-1).getMaxGenerationsToStop());
			// Schedule key generation part
			//			settingsCheckboxes.get(0).setSelected(previousSettings.get(previousSettings.size()-1).isScheduledKeyGeneration());
			//			minutesField.setText(Integer.toString(previousSettings.get(previousSettings.size()-1).getScheduledKeyGenerationTime()));
			settingsCheckboxes.get(0).setSelected(previousSettings.get(previousSettings.size()-1).isWriteLogActive());

		}

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));

		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Save user values to database
				// Verify that all fields are positive numbers and aren't empty
				try {
					try {
						Double.parseDouble(finalMutationRateField.getText());
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(null, "Mutation rate must be a number.");
					}
					if(finalMutationRateField != null && Double.parseDouble(finalMutationRateField.getText()) >= 0) {
						newSettings.setIndividualSize(settingsSliders.get(0).getValue());
						newSettings.setPopulationSize(settingsSliders.get(1).getValue());
						newSettings.setNumOfCrossoverPoints(settingsSliders.get(2).getValue());
						newSettings.setNumOfMutationsPerIndividual(settingsSliders.get(3).getValue());
						newSettings.setMutationRate(Double.parseDouble(finalMutationRateField.getText().toString()));
						newSettings.setMaxPreservedIndividuals(settingsSliders.get(4).getValue());
						newSettings.setNumOfFitIndividualsToStop(settingsSliders.get(5).getValue());
						newSettings.setMaxGenerationsToStop(settingsSliders.get(6).getValue());
						// Schedule key generation part
						//						newSettings.setScheduledKeyGeneration(settingsCheckboxes.get(0).isSelected());
						newSettings.setWriteLogActive(settingsCheckboxes.get(0).isSelected());

						// Apply the newly set parameters
						Settings.setIndividualSize(newSettings.getIndividualSize());
						Settings.setInitialPopulationSize(newSettings.getPopulationSize());
						Settings.setNumOfCrossoverPoints(newSettings.getNumOfCrossoverPoints());
						Settings.setNumOfMutationsPerIndividual(newSettings.getNumOfMutationsPerIndividual());
						Settings.setMutationRate(newSettings.getMutationRate());
						Settings.setMaxPopulationSize(newSettings.getMaxPreservedIndividuals());
						Settings.setNumOfFitIndividualsToStop(newSettings.getNumOfFitIndividualsToStop());
						Settings.setMaxGenerationsToStop(newSettings.getMaxGenerationsToStop());
						// Schedule key generation part
						//						Settings.setScheduledKeyGeneration(newSettings.isScheduledKeyGeneration());
						//						Settings.setScheduledKeyGenerationTime(newSettings.getScheduledKeyGenerationTime());
						Settings.setWriteLogActive(newSettings.isWriteLogActive());

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
				for(int i = 0; i <= settingsSliders.size(); i++) {
					if(i == 4) { // Mutation rate index field
						finalMutationRateField.setText(defaultSettingsValues[i]);
					}else if(i > 4) {
						settingsSliders.get(i-1).setValue(Integer.parseInt(defaultSettingsValues[i]));
					}else {
						settingsSliders.get(i).setValue(Integer.parseInt(defaultSettingsValues[i]));
					}
				}
				// Schedule key generation part
				//				settingsCheckboxes.get(0).setSelected(false);
				settingsCheckboxes.get(0).setSelected(false);
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
