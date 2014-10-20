package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.PasswordManager;
import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;
import br.pucc.engComp.GenCryptoKey.controller.FieldValidators;

@SuppressWarnings(value = { "serial" })
public class EditUser extends JFrame implements FocusListener{

	public EditUser(JFrame homeFrame){
		final JDialog editUserFrame = new JDialog(homeFrame, "Edit user info", true);
		String[] editUserInfoLabels = {"First name: ", "Last name: ", "E-Mail: ", "Confirm E-Mail: ",
				"Username: ", "Password: ", "Confirm password: "};
		String[] editUserValidationLabels = {"name", "name", "email", "email", "", "pass", "pass"};
		int numLabels = editUserInfoLabels.length;
		final UserPOJO editUser = new UserPOJO();
		final ArrayList<JTextField> valueFields = new ArrayList<JTextField>();

		JPanel parentPanel = new JPanel(new BorderLayout());

		// Formats the panel and creates the grid
		JPanel springPanel = new JPanel(new SpringLayout());
		springPanel.setOpaque(true);

		for (int i = 0; i < numLabels; i++) {
			JLabel l = new JLabel(editUserInfoLabels[i], JLabel.TRAILING);
			springPanel.add(l);
			if(l.getText().toLowerCase().contains("password")){
				JPasswordField passwordTextField = new JPasswordField(20);
				passwordTextField.setName(editUserValidationLabels[i]);
				l.setLabelFor(passwordTextField);
				springPanel.add(passwordTextField);
				valueFields.add(passwordTextField);
				valueFields.get(i).addFocusListener(this);
			}else{
				JTextField editUserInfoTextField = new JTextField(20);
				editUserInfoTextField.setName(editUserValidationLabels[i]);
				l.setLabelFor(editUserInfoTextField);
				springPanel.add(editUserInfoTextField);
				valueFields.add(editUserInfoTextField);
				valueFields.get(i).addFocusListener(this);
				valueFields.get(i).addFocusListener(this);
			}
		}

		// Formats the panel and creates the grid
		SpringUtilities.makeCompactGrid(springPanel,
				numLabels, 2, // lines, columns
				6, 6,        // initX, initY
				6, 6);       // xPad, yPad

		try {
			ArrayList<UserPOJO> registeredUser = UserDAO.getUsers();
			valueFields.get(0).setText(registeredUser.get(0).getFirstName());
			valueFields.get(1).setText(registeredUser.get(0).getLastName());
			valueFields.get(2).setText(registeredUser.get(0).getEmail());
			valueFields.get(3).setText(registeredUser.get(0).getEmail());
			valueFields.get(4).setText(registeredUser.get(0).getUsername());
			valueFields.get(4).setEnabled(false);
			// Not a good idea to show how long the password is
			//valueFields.get(5).setText(registeredUser.get(0).getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured trying to update your user information.", "Database error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error loading user info from database: " + e.getMessage());
		}

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));

		JButton saveChangesButton = new JButton("Save changes");
		saveChangesButton.addActionListener(new ActionListener(){
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

				} else if(!FieldValidators.NameValidator(valueFields.get(0).getText()) || !FieldValidators.NameValidator(valueFields.get(1).getText())) {
					// Name or last name field is invalid -> warn user
					JOptionPane.showMessageDialog(null, "Names may not contain whitespace, numbers or special characters (exceptions: - and ')", "Invalid name", JOptionPane.ERROR_MESSAGE);
				} else if(!FieldValidators.EmailValidator(valueFields.get(2).getText()) || !FieldValidators.EmailValidator(valueFields.get(3).getText())) {
					// E-mail or E-Mail confirmation is invalid -> warn user
					JOptionPane.showMessageDialog(null, "Please enter a valid e-mail address.", "Invalid e-mail", JOptionPane.ERROR_MESSAGE);
				} else if(!valueFields.get(2).getText().equals(valueFields.get(3).getText())){
					// E-mail & E-Mail confirmation fields have different values -> warn user
					JOptionPane.showMessageDialog(null, "The e-mail confirmation address does not match.", "E-mail mismatch", JOptionPane.ERROR_MESSAGE);
				} else if(!valueFields.get(5).getText().equals(valueFields.get(6).getText())){
					// Password & Password confirmation fields have different values -> warn user
					JOptionPane.showMessageDialog(null, "The password confirmation does not match the password.", "Password mismatch", JOptionPane.ERROR_MESSAGE);
				} else{
					// If more than 1 user is allowed
					//Check if Username and or E-Mail isn't already in use
					editUser.setFirstName(valueFields.get(0).getText());
					editUser.setLastName(valueFields.get(1).getText());
					editUser.setEmail(valueFields.get(2).getText());
					editUser.setUsername(valueFields.get(4).getText());

					// Calculate SHA-256 hash of the user's input password before storing in database
					PasswordManager pwManager = new PasswordManager();
					String userInputPassword = pwManager.calculateSha256HashString(valueFields.get(5).getText().toString());

					editUser.setPassword(userInputPassword);

					try{
						if(UserDAO.updateUser(editUser, false) != -1) {
							editUserFrame.dispose();
							JOptionPane.showMessageDialog(null, "Your user information has been succesfully updated.", "Update successful", JOptionPane.INFORMATION_MESSAGE);
						}
					}catch (Exception e) {
						editUserFrame.dispose();
						JOptionPane.showMessageDialog(null, "An error occured trying to update your user information.", "Database error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						DB.printSQLException((SQLException) e);
						// TODO: Tem erro aqui... Tente trocar a senha para Abc12#
					}
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				editUserFrame.dispose();
			}
		});

		JButton deleteUserButton = new JButton("Delete user");
		deleteUserButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				int confirmDeletion = JOptionPane.showConfirmDialog(null, "By deleting your user account, you automatically unregister\n from the application.\n Do you wish to proceed?", "Unregister confirmation", JOptionPane.YES_NO_OPTION);
				if(confirmDeletion == JOptionPane.YES_OPTION) {
					try{
						if(UserDAO.deleteUser(editUser) != -1) {
							editUserFrame.dispose();
							JOptionPane.showConfirmDialog(null, "Your user account has been successfully unregistered from the application.", "Unregister successful", JOptionPane.YES_NO_OPTION);
						}
					}catch (Exception e) {
						editUserFrame.dispose();
						JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		buttonsPanel.add(saveChangesButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(deleteUserButton);

		parentPanel.add(springPanel, BorderLayout.NORTH);
		parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
		editUserFrame.setContentPane(parentPanel);
		editUserFrame.pack();
		editUserFrame.setResizable(false);
		editUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editUserFrame.setLocationRelativeTo(null);
		// The "Register" button automatically listens to the 'Enter' key
		editUserFrame.getRootPane().setDefaultButton(saveChangesButton);
		editUserFrame.setVisible(true);
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
		case "pass":
			if(!FieldValidators.PasswordValidator(textField.getText())) {
				textField.setBackground(new Color(250, 30, 45));
				System.out.println(" entrando no case pass!!!!!");
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
