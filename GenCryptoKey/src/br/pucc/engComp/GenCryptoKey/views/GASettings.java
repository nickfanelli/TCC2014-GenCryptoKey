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
				"Percentage of individuals to cross: ", "Maximum population size: ", "Maximum number of generations: "};

		final String[] defaultSettingsValues = {"1024",   "500",  "1",  "1",  "0.015",   "0.5",    "50",    "20"};
		String[] minimumParameterValues      = {"128",     "50",  "1",  "1",  "0.000",  "0.05",    "50",    "10"};
		String[] maximumParameterValues      = {"4096",  "1000",  "2",  "2",   "0.03",   "1.0",  "1000",  "2000"};

		final int numLabels = settingsLabels.length;

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
		final JTextField finalDisplayMutationRateValue = new JTextField();
		JTextField percentIndividualsToCross = null;
		final JTextField finalDisplayPercentToCrossValue = new JTextField();
		for (int i = 0; i < numLabels; i++) {
			// Creating settings components ...
			// Parameter label
			JLabel parameterName = new JLabel(settingsLabels[i], JLabel.TRAILING);
			springPanel.add(parameterName);
			// Parameter slider or text field
			if(i == 4){
				newMutationRateField = new JTextField(defaultSettingsValues[i]);
				parameterName.setLabelFor(newMutationRateField);
				newMutationRateField.setEnabled(true);
				springPanel.add(newMutationRateField);

				finalDisplayMutationRateValue.setText(newMutationRateField.getText()); //= new JTextField(newMutationRateField.getText());
				finalDisplayMutationRateValue.setName(parameterName.getName());
				finalDisplayMutationRateValue.setEnabled(false);
				springPanel.add(finalDisplayMutationRateValue);
				settingsValueDisplay.add(finalDisplayMutationRateValue);

				final JTextField mutationRateFieldAux = newMutationRateField;
				newMutationRateField.addKeyListener(new KeyListener() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						finalDisplayMutationRateValue.setText(mutationRateFieldAux.getText());
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

			} else if(i == 5){
				// FIXME: Use JFormattedTextField to limit input characters and input size
				percentIndividualsToCross = new JTextField(defaultSettingsValues[i]);
				parameterName.setLabelFor(percentIndividualsToCross);
				percentIndividualsToCross.setEnabled(true);
				springPanel.add(percentIndividualsToCross);

				finalDisplayPercentToCrossValue.setText(percentIndividualsToCross.getText()); //= new JTextField(percentIndividualsToCross.getText());
				finalDisplayPercentToCrossValue.setName(parameterName.getName());
				finalDisplayPercentToCrossValue.setEnabled(false);
				springPanel.add(finalDisplayPercentToCrossValue);
				settingsValueDisplay.add(finalDisplayPercentToCrossValue);

				final JTextField percentIndividualsToCrossFieldAux = percentIndividualsToCross;
				percentIndividualsToCross.addKeyListener(new KeyListener() {
					@Override
					public void keyReleased(KeyEvent arg0) {
						finalDisplayPercentToCrossValue.setText(percentIndividualsToCrossFieldAux.getText());
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

			} else{
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
		final JTextField finalPercentIndividualsToCross = percentIndividualsToCross;

		// Adding ActionListener to each individual setting Reset button
		for(int i = 0; i < settingsResetButtons.size(); i++) {
			final int aux = i;
			if(i == 4) { // Skip the mutation rate on the defaultSettingsValues list
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						finalMutationRateField.setText(defaultSettingsValues[aux]);
						finalDisplayMutationRateValue.setText(defaultSettingsValues[aux]);
					}
				});
			} else if (i == 5) {
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						finalPercentIndividualsToCross.setText(defaultSettingsValues[aux]);
						finalDisplayPercentToCrossValue.setText(defaultSettingsValues[aux]);
					}
				});
			} else if(i > 5) {
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						settingsSliders.get(aux-2).setValue(Integer.parseInt(defaultSettingsValues[aux]));
					}
				});
			} else {
				settingsResetButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						settingsSliders.get(aux).setValue(Integer.parseInt(defaultSettingsValues[aux]));
					}
				});
			}
		}

		//         TODO Schedule Key Generation: not going to be implemented in the 1st version
		//        		JLabel scheduleLabel = new JLabel("Schedule key generation: ");
		//        		final JCheckBox scheduleCheckBox = new JCheckBox();
		//        		scheduleLabel.setLabelFor(scheduleCheckBox);
		//        		settingsCheckboxes.add(scheduleCheckBox);
		//        		JTextField scheduleTextField = new JTextField("No");
		//        		scheduleTextField.setEnabled(false);
		//        		JButton resetScheduleButton = new JButton("Reset");
		//        		resetScheduleButton.addActionListener(new ActionListener() {
		//        			@Override
		//        			public void actionPerformed(ActionEvent evt) {
		//        				scheduleCheckBox.setSelected(false);
		//        			}
		//        		});
		//
		//        		final JTextField minutesField = new JTextField();
		//        		scheduleCheckBox.addActionListener(new ActionListener() {
		//        			@Override
		//        			public void actionPerformed(ActionEvent evt) {
		//        				if(scheduleCheckBox.isSelected()) {
		//        					final JDialog scheduleKeyFrame = new JDialog(settingsFrame, "Schedule key generation", true);
		//        					scheduleKeyFrame.setSize(300, 100);
		//        					JPanel scheduleKeyPanel = new JPanel();
		//        					JPanel scheduleKeyTextPanel = new JPanel();
		//        					scheduleKeyPanel.setLayout(new GridLayout(2, 2, 10, 0));
		//        					scheduleKeyTextPanel.setLayout(new GridLayout(1, 2, 10, 0));
		//        					JLabel scheduleKeyLabel = new JLabel("Generate keys every: ");
		//        					JLabel minutesLabel = new JLabel("minutes.");
		//
		//        					minutesField.setSize(20, 10);
		//
		//        					JButton applyScheduleKeyButton = new JButton("Apply");
		//        					JButton cancelScheduleKeyButton = new JButton("Cancel");
		//
		//        					applyScheduleKeyButton.addActionListener(new ActionListener() {
		//        						@Override
		//        						public void actionPerformed(ActionEvent evt) {
		//        							newSettings.setScheduledKeyGenerationTime(Integer.parseInt(minutesField.getText()));
		//        							scheduleKeyFrame.dispose();
		//        						}
		//        					});
		//
		//        					cancelScheduleKeyButton.addActionListener(new ActionListener() {
		//        						@Override
		//        						public void actionPerformed(ActionEvent evt) {
		//        							scheduleKeyFrame.dispose();
		//        						}
		//        					});
		//
		//        					scheduleKeyPanel.add(scheduleKeyLabel);
		//        					scheduleKeyTextPanel.add(minutesField);
		//        					scheduleKeyTextPanel.add(minutesLabel);
		//        					scheduleKeyPanel.add(scheduleKeyTextPanel);
		//        					scheduleKeyPanel.add(applyScheduleKeyButton);
		//        					scheduleKeyPanel.add(cancelScheduleKeyButton);
		//
		//        					scheduleKeyFrame.setContentPane(scheduleKeyPanel);
		//        					scheduleKeyFrame.pack();
		//        					scheduleKeyFrame.validate();
		//        					scheduleKeyFrame.setResizable(false);
		//        					scheduleKeyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//        					scheduleKeyFrame.setLocationRelativeTo(null);
		//        					scheduleKeyFrame.setVisible(true);
		//        				}else {
		//        					newSettings.setScheduledKeyGenerationTime(0);
		//        				}
		//        			}
		//        		});
		//
		//        		springPanel.add(scheduleLabel);
		//        		springPanel.add(scheduleCheckBox);
		//        		springPanel.add(scheduleTextField);
		//        		springPanel.add(resetScheduleButton);


		JLabel useKSTestLabel = new JLabel("Use Kolmogorov-Smirnov Test: ");
		final JCheckBox useKSTestCheckBox = new JCheckBox();
		useKSTestCheckBox.setSelected(true);
		useKSTestLabel.setLabelFor(useKSTestCheckBox);
		settingsCheckboxes.add(useKSTestCheckBox);
		final JTextField useKSTestTextField = new JTextField("Yes");
		useKSTestTextField.setEnabled(false);

		useKSTestCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(useKSTestCheckBox.isSelected()) {
					useKSTestTextField.setText("Yes");
				} else {
					useKSTestTextField.setText("No");
				}
			}
		});

		JButton resetUseKSTestButton = new JButton("Reset");
		resetUseKSTestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				useKSTestCheckBox.setSelected(true);
				useKSTestTextField.setText("Yes");
			}
		});

		springPanel.add(useKSTestLabel);
		springPanel.add(useKSTestCheckBox);
		springPanel.add(useKSTestTextField);
		springPanel.add(resetUseKSTestButton);

		JLabel useChiSquareTestLabel = new JLabel("Use Chi-square Test: ");
		final JCheckBox useChiSquareTestCheckBox = new JCheckBox();
		useChiSquareTestCheckBox.setSelected(true);
		useChiSquareTestLabel.setLabelFor(useChiSquareTestCheckBox);
		settingsCheckboxes.add(useChiSquareTestCheckBox);
		final JTextField useChiSquareTestTextField = new JTextField("Yes");
		useChiSquareTestTextField.setEnabled(false);

		useChiSquareTestCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(useChiSquareTestCheckBox.isSelected()) {
					useChiSquareTestTextField.setText("Yes");
				} else {
					useChiSquareTestTextField.setText("No");
				}
			}
		});

		JButton resetUseChiSquareTestButton = new JButton("Reset");
		resetUseChiSquareTestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				useChiSquareTestCheckBox.setSelected(true);
				useChiSquareTestTextField.setText("Yes");
			}
		});

		springPanel.add(useChiSquareTestLabel);
		springPanel.add(useChiSquareTestCheckBox);
		springPanel.add(useChiSquareTestTextField);
		springPanel.add(resetUseChiSquareTestButton);

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
				writeLogTextField.setText("No");
			}
		});

		springPanel.add(writeLogLabel);
		springPanel.add(writeLogCheckBox);
		springPanel.add(writeLogTextField);
		springPanel.add(resetWriteLogButton);

		// Formats the panel and creates the grid
		// Number of lines = labels(sliders + text fields) + checkboxes.
		// Schedule Key Generation will add 1 to checkboxes when implemented
		SpringUtilities.makeCompactGrid(springPanel,
				(numLabels + settingsCheckboxes.size()), 4, // lines, columns
				6, 6,        // initX, initY
				6, 6);       // xPad, yPad

		// If there are saved settings on DB, populate them
		ArrayList<SettingsPOJO> previousSettings = null;
		try {
			previousSettings = SettingsDAO.getAllSettingsProfiles();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(previousSettings != null && !previousSettings.isEmpty()) { // Use the last saved settings --> (previousSettings.size()-1)
			settingsSliders.get(0).setValue(previousSettings.get(previousSettings.size()-1).getIndividualSize());
			settingsSliders.get(1).setValue(previousSettings.get(previousSettings.size()-1).getInitialPopulationSize());
			settingsSliders.get(2).setValue(previousSettings.get(previousSettings.size()-1).getNumOfCrossoverPoints());
			settingsSliders.get(3).setValue(previousSettings.get(previousSettings.size()-1).getNumOfMutationsPerIndividual());
			finalMutationRateField.setText(Double.toString(previousSettings.get(previousSettings.size()-1).getMutationRate()));
			finalDisplayMutationRateValue.setText(finalMutationRateField.getText());
			finalPercentIndividualsToCross.setText(Double.toString(previousSettings.get(previousSettings.size()-1).getPercentageOfIndividualsToCross()));
			finalDisplayPercentToCrossValue.setText(finalPercentIndividualsToCross.getText());
			settingsSliders.get(4).setValue(previousSettings.get(previousSettings.size()-1).getMaxPopulationSize());
			settingsSliders.get(5).setValue(previousSettings.get(previousSettings.size()-1).getMaxGenerationsToStop());
			// Schedule key generation part
			//			settingsCheckboxes.get(0).setSelected(previousSettings.get(previousSettings.size()-1).isScheduledKeyGeneration());
			//			minutesField.setText(Integer.toString(previousSettings.get(previousSettings.size()-1).getScheduledKeyGenerationTime()));
			settingsCheckboxes.get(0).setSelected(previousSettings.get(previousSettings.size()-1).isWriteLogActive());
			if(settingsCheckboxes.get(0).isSelected()){
				writeLogTextField.setText("Yes");
			}
		}

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));

		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Verify that all fields are positive numbers and aren't empty
				try {
					try {
						Double.parseDouble(finalMutationRateField.getText());
						Double.parseDouble(finalPercentIndividualsToCross.getText());
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(null, "\"Mutation rate\" and \"Percentage of individuals to cross\" must be numbers.");
					}
					if(finalMutationRateField != null && Double.parseDouble(finalMutationRateField.getText()) > 0 && Double.parseDouble(finalMutationRateField.getText()) <= 1
							&& finalPercentIndividualsToCross != null && Double.parseDouble(finalPercentIndividualsToCross.getText()) >= 0.02 && Double.parseDouble(finalPercentIndividualsToCross.getText()) <= 1) {
						newSettings.setIndividualSize(settingsSliders.get(0).getValue());
						newSettings.setInitialPopulationSize(settingsSliders.get(1).getValue());
						newSettings.setNumOfCrossoverPoints(settingsSliders.get(2).getValue());
						newSettings.setNumOfMutationsPerIndividual(settingsSliders.get(3).getValue());
						newSettings.setMutationRate(Double.parseDouble(finalMutationRateField.getText().toString()));
						newSettings.setPercentageOfIndividualsToCross(Double.parseDouble(finalPercentIndividualsToCross.getText().toString()));
						newSettings.setMaxPopulationSize(settingsSliders.get(4).getValue());
						newSettings.setMaxGenerationsToStop(settingsSliders.get(5).getValue());
						// Schedule key generation part
						//						newSettings.setScheduledKeyGeneration(settingsCheckboxes.get(0).isSelected());
						newSettings.setWriteLogActive(settingsCheckboxes.get(0).isSelected());

						// Apply the newly set parameters
						Settings.setIndividualSize(newSettings.getIndividualSize());
						Settings.setInitialPopulationSize(newSettings.getInitialPopulationSize());
						Settings.setNumOfCrossoverPoints(newSettings.getNumOfCrossoverPoints());
						Settings.setNumOfMutationsPerIndividual(newSettings.getNumOfMutationsPerIndividual());
						Settings.setMutationRate(newSettings.getMutationRate());
						Settings.setPercentageOfIndividualsToCross(newSettings.getPercentageOfIndividualsToCross());
						Settings.setMaxPopulationSize(newSettings.getMaxPopulationSize());
						Settings.setMaxGenerationsToStop(newSettings.getMaxGenerationsToStop());
						// Schedule key generation part
						//						Settings.setScheduledKeyGeneration(newSettings.isScheduledKeyGeneration());
						//						Settings.setScheduledKeyGenerationTime(newSettings.getScheduledKeyGenerationTime());
						Settings.setWriteLogActive(newSettings.isWriteLogActive());

						// Save to database
						if(SettingsDAO.newSettings(newSettings) != -1) {
							System.out.println("[LOG - INFO] -- New settings successfully saved to the database.");
							settingsFrame.dispose();
						}
					}else {
						JOptionPane.showMessageDialog(null, "<html>\"Mutation rate\" must be a <b>positive</b> value <b>between 0 and 1</b><br> and \"Percentage individuals to cross\" must be <b>positive</b> values <b>between 0.02 and 1</b>.</html>", "Illegal value", JOptionPane.ERROR_MESSAGE);
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
				for(int i = 0; i < numLabels; i++) {
					if(i == 4) { // Mutation rate field
						finalMutationRateField.setText(defaultSettingsValues[i]);
						finalDisplayMutationRateValue.setText(defaultSettingsValues[i]);
					} else if (i==5) { // Percentage of individuals to cross field
						finalPercentIndividualsToCross.setText(defaultSettingsValues[i]);
						finalDisplayPercentToCrossValue.setText(defaultSettingsValues[i]);
					} else if(i > 5) {
						settingsSliders.get(i-2).setValue(Integer.parseInt(defaultSettingsValues[i]));
					} else {
						settingsSliders.get(i).setValue(Integer.parseInt(defaultSettingsValues[i]));
					}
				}
				// Schedule key generation part
				//                settingsCheckboxes.get(0).setSelected(false);
				//                scheduleTextField.setText("No");

				useKSTestCheckBox.setSelected(true);
				useKSTestTextField.setText("Yes");

				useChiSquareTestCheckBox.setSelected(true);
				useChiSquareTestTextField.setText("Yes");

				writeLogCheckBox.setSelected(false);
				writeLogTextField.setText("No");
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
