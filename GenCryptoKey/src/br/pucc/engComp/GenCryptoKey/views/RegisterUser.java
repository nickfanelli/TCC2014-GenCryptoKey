package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import br.pucc.engComp.GenCryptoKey.controller.PasswordManager;
import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;
import br.pucc.engComp.GenCryptoKey.controller.FieldValidators;

@SuppressWarnings(value = { "serial" })
public class RegisterUser extends JFrame implements FocusListener{

	public RegisterUser(JFrame homeFrame, final JMenuItem fileRegisterUserMenu, final JMenuItem fileLoginUserMenu){
		final JDialog registerUserFrame = new JDialog(homeFrame, "Register user", true);
		String[] registerUserLabels = {"First name: ", "Last name: ", "E-Mail: ", "Confirm E-Mail: ",
				"Username: ", "Password: ", "Confirm password: "};
		String[] registerUserValidationLabels = {"name", "name", "email", "email", "user", "pass", "pass"};
		int numLabels = registerUserLabels.length;

		final ArrayList<JTextField> valueFields = new ArrayList<JTextField>();

		JPanel parentPanel = new JPanel(new BorderLayout());

		// Creates and populates the panel for the grid
		JPanel springPanel = new JPanel(new SpringLayout());
		springPanel.setOpaque(true);

		for (int i = 0; i < numLabels; i++) {
			JLabel l = new JLabel(registerUserLabels[i], JLabel.TRAILING);
			springPanel.add(l);
			if(l.getText().toLowerCase().contains("password")){
				JPasswordField passwordTextField = new JPasswordField(20);
				passwordTextField.setName(registerUserValidationLabels[i]);
				l.setLabelFor(passwordTextField);
				springPanel.add(passwordTextField);
				valueFields.add(passwordTextField);
				valueFields.get(i).addFocusListener(this);
			}else{
				JTextField userInfoTextField = new JTextField(20);
				userInfoTextField.setName(registerUserValidationLabels[i]);
				l.setLabelFor(userInfoTextField);
				springPanel.add(userInfoTextField);
				valueFields.add(userInfoTextField);
				valueFields.get(i).addFocusListener(this);
				valueFields.get(i).addFocusListener(this);
			}
		}

		// Formats the panel and creates the grid
		SpringUtilities.makeCompactGrid(springPanel,
				numLabels, 2, // lines, columns
				6, 6,        // initX, initY
				6, 6);       // xPad, yPad


		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

		final JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Verify no field is empty before proceeding
				if(valueFields.get(0).getText().isEmpty()
						|| valueFields.get(1).getText().isEmpty()
						|| valueFields.get(2).getText().isEmpty()
						|| valueFields.get(3).getText().isEmpty()
						|| valueFields.get(4).getText().isEmpty()
						|| valueFields.get(5).getText().isEmpty()
						|| valueFields.get(6).getText().isEmpty()) {
					// One or more fields are empty -> warn user
					JOptionPane.showMessageDialog(null, "Please fill in all the fields.", "Blank fields", JOptionPane.ERROR_MESSAGE);

				}else if(!FieldValidators.NameValidator(valueFields.get(0).getText()) || !FieldValidators.NameValidator(valueFields.get(1).getText())) {
					// Name or last name field is invalid -> warn user
					JOptionPane.showMessageDialog(null, "Names may not contain whitespace, numbers or special characters (exceptions: - and ')", "Invalid name", JOptionPane.ERROR_MESSAGE);
				}else if(!FieldValidators.EmailValidator(valueFields.get(2).getText()) || !FieldValidators.EmailValidator(valueFields.get(3).getText())) {
					// E-mail or E-Mail confirmation is invalid -> warn user
					JOptionPane.showMessageDialog(null, "Please enter a valid e-mail address.", "Invalid e-mail", JOptionPane.ERROR_MESSAGE);
				}else if(!valueFields.get(2).getText().equals(valueFields.get(3).getText())){
					// E-mail & E-Mail confirmation fields have different values -> warn user
					JOptionPane.showMessageDialog(null, "The e-mail confirmation address does not match.", "E-mail mismatch", JOptionPane.ERROR_MESSAGE);
				}else if(!valueFields.get(5).getText().equals(valueFields.get(6).getText())){
					// Password & Password confirmation fields have different values -> warn user
					JOptionPane.showMessageDialog(null, "The password confirmation does not match the password.", "Password mismatch", JOptionPane.ERROR_MESSAGE);
				}else{
					// If more than 1 user is allowed
					//Check if Username and or E-Mail isn't already in use
					UserPOJO newUser = new UserPOJO();
					newUser.setFirstName(valueFields.get(0).getText());
					newUser.setLastName(valueFields.get(1).getText());
					newUser.setEmail(valueFields.get(2).getText());
					newUser.setUsername(valueFields.get(4).getText());

					// Calculate SHA-256 hash of the user's input password before storing in database
					PasswordManager pwManager = new PasswordManager();
					String userInputPasswordHash = pwManager.calculateSha256HashString(valueFields.get(5).getText());
					// Generate a backup password in case the user forgets his/her own
					// and calculate its SHA-256 hash of storing in database
					String autoGeneratedBackupPassword = pwManager.generateBackupPassword();
					String autoGeneratedBackupPasswordHash = pwManager.calculateSha256HashString(autoGeneratedBackupPassword);

					newUser.setPassword(userInputPasswordHash);
					newUser.setBackupPassword(autoGeneratedBackupPassword);
					newUser.setBackupPasswordHash(autoGeneratedBackupPasswordHash);

					System.out.println("autoGeneratedBackupPassword: " + autoGeneratedBackupPassword);
					System.out.println("autoGeneratedBackupPasswordHash: " + autoGeneratedBackupPasswordHash);
					try {
						if(UserDAO.newUser(newUser) != -1) {
							System.out.println("User successfully registered.");
							registerUserFrame.dispose();
							JOptionPane.showMessageDialog(null, "User successfully registered.", "Register success", JOptionPane.INFORMATION_MESSAGE);
							fileRegisterUserMenu.setEnabled(false);
							fileLoginUserMenu.setEnabled(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
						registerUserFrame.dispose();
						JOptionPane.showMessageDialog(null, "An error occured trying to update your user information.", "Database error", JOptionPane.ERROR_MESSAGE);
						System.out.println("Error loading user info from database: " + e.getMessage());
					}
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				registerUserFrame.dispose();
			}
		});

		buttonsPanel.add(registerButton);
		buttonsPanel.add(cancelButton);

		parentPanel.add(springPanel, BorderLayout.NORTH);
		parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
		registerUserFrame.setContentPane(parentPanel);
		registerUserFrame.pack();
		registerUserFrame.setResizable(false);
		registerUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		registerUserFrame.setLocationRelativeTo(null);
		// The "Register" button automatically listens to the 'Enter' key
		registerUserFrame.getRootPane().setDefaultButton(registerButton);
		registerUserFrame.setVisible(true);
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// Do nothing
	}

	@Override
	public void focusLost(FocusEvent e) {
		// Perform input validation
		JTextField textField = (JTextField) e.getComponent();
		switch(textField.getName()){
		case "name":
			if(!FieldValidators.NameValidator(textField.getText())) {
				textField.setBackground(new Color(250, 30, 45));
			} else {
				textField.setBackground(new Color(0, 200, 40));
			}
			break;
		case "email":
			if(!FieldValidators.EmailValidator(textField.getText())) {
				textField.setBackground(new Color(250, 30, 45));
			} else {
				textField.setBackground(new Color(0, 200, 40));
			}
			break;
		case "user":
			if(!FieldValidators.UsernameValidator(textField.getText())) {
				textField.setBackground(new Color(250, 30, 45));
			} else {
				textField.setBackground(new Color(0, 200, 40));
			}
			break;
		case "pass":
			if(!FieldValidators.PasswordValidator(textField.getText())) {
				textField.setBackground(new Color(250, 30, 45));
				// TODO FIX: Message Dialog is popping up more than once
				//JOptionPane.showMessageDialog(null, "The password must contain at least one lowercase and one uppercase letter,\n one digit, one special character and be 8-30 characters long.", "Invalid password", JOptionPane.WARNING_MESSAGE);
				//textField.requestFocus();
			} else {
				textField.setBackground(new Color(0, 200, 40));
			}
			break;
		default:
			break;
		}
	}
}
