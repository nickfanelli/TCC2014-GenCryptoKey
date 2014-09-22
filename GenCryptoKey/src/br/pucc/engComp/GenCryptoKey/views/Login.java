package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

@SuppressWarnings(value = { "serial" })
public class Login extends JFrame{

	public Login(JFrame homeFrame, final JMenuItem fileLoginMenu, final JMenuItem fileLogoutMenu, final JMenuItem editUserInfoMenu, final JMenuItem editSettingsMenu,
			final JMenuItem runGenerateKeyMenu, final JMenuItem runGenerateKeyGraphicallyMenu,
			final JMenuItem viewLastGeneratedKeyMenu, final JMenuItem viewExecutionLogMenu) {
		final JDialog loginFrame = new JDialog(homeFrame, "Login", true);
		String[] loginLabels = {"Username: ", "Password: "};
		int numLabels = loginLabels.length;

		final ArrayList<JTextField> valueFields = new ArrayList<JTextField>();

		JPanel parentPanel = new JPanel(new BorderLayout());

		// Formats the panel and creates the grid
		JPanel springPanel = new JPanel(new SpringLayout());
		springPanel.setOpaque(true);

		for (int i = 0; i < numLabels; i++) {
			JLabel l = new JLabel(loginLabels[i], JLabel.TRAILING);
			springPanel.add(l);
			if(l.getText().toLowerCase().contains("password")){
				JPasswordField passwordTextField = new JPasswordField(10);
				l.setLabelFor(passwordTextField);
				springPanel.add(passwordTextField);
				valueFields.add(passwordTextField);
			}else{
				JTextField loginTextField = new JTextField(10);
				l.setLabelFor(loginTextField);
				springPanel.add(loginTextField);
				valueFields.add(loginTextField);
			}
		}

		// Formats the panel and creates the grid
		SpringUtilities.makeCompactGrid(springPanel,
				numLabels, 2, // lines, columns
				6, 6,        // initX, initY
				6, 6);       // xPad, yPad


		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));

		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evtvt) {
				if(!valueFields.get(0).getText().isEmpty() && !valueFields.get(1).getText().isEmpty()) {
					UserPOJO user = new UserPOJO();

					user.setUsername(valueFields.get(0).getText());

					// Calculate SHA-256 hash of the user's input password
					PasswordManager pwManager = new PasswordManager();
					String userInputPasswordHash = pwManager.calculateSha256HashString(valueFields.get(1).getText());

					user.setPassword(userInputPasswordHash);
					user.setBackupPasswordHash(userInputPasswordHash);

					try {
						if(UserDAO.getInstance().isRegistered(user)) {
							System.out.println("User successfully logged in.");
							fileLoginMenu.setEnabled(false);
							fileLogoutMenu.setEnabled(true);
							editUserInfoMenu.setEnabled(true);
							editSettingsMenu.setEnabled(true);
							runGenerateKeyMenu.setEnabled(true);
							// TODO Set to true once these functionalities are implemented.
							runGenerateKeyGraphicallyMenu.setEnabled(false);
							viewLastGeneratedKeyMenu.setEnabled(false);
							viewExecutionLogMenu.setEnabled(false);
							loginFrame.dispose();
						}else if(UserDAO.getInstance().isLoggingInWithBackupPassword(user)){
							System.out.println("User successfully logged in.");
							fileLoginMenu.setEnabled(false);
							fileLogoutMenu.setEnabled(true);
							editUserInfoMenu.setEnabled(true);
							editSettingsMenu.setEnabled(true);
							runGenerateKeyMenu.setEnabled(true);
							// TODO Set to true once these functionalities are implemented.
							runGenerateKeyGraphicallyMenu.setEnabled(false);
							viewLastGeneratedKeyMenu.setEnabled(false);
							viewExecutionLogMenu.setEnabled(false);
							JOptionPane.showMessageDialog(null, "You logged in using your backup password, which has now been changed. It is recommended you now update your password.", "Backup login", JOptionPane.INFORMATION_MESSAGE);
							loginFrame.dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "Username and/or password are incorrect.", "Invalid login", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
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
				loginFrame.dispose();
			}
		});
		JButton resetPasswordButton = new JButton("Forgot my password");
		resetPasswordButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				UserPOJO registeredUser;
				PasswordManager pwManager = new PasswordManager();
				String newBackupPassword = pwManager.generateBackupPassword();

				try {
					registeredUser = UserDAO.getInstance().getUsers().get(0);
					registeredUser.setBackupPassword(newBackupPassword);
					registeredUser.setBackupPasswordHash(pwManager.calculateSha256HashString(newBackupPassword));

					try{
						if(UserDAO.getInstance().updateUser(registeredUser, true) == 1) {
							// Success updating the backup password -> send e-mail to user
							pwManager.sendPasswordRecoveryEmail();
							JOptionPane.showMessageDialog(null, "An e-mail has been sent to your registered e-mail address with\n password recovery instructions.", "Password recovery", JOptionPane.INFORMATION_MESSAGE);
						}
					}catch (Exception e) {
						JOptionPane.showMessageDialog(null, "An error occured trying to update your backup password.", "Database error", JOptionPane.ERROR_MESSAGE);
						System.out.println("Error message: " + e.getMessage());
						e.printStackTrace();
					}

				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "There is no registered user.", "Database error", JOptionPane.ERROR_MESSAGE);
					System.out.println("Error message: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});

		buttonsPanel.add(loginButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(resetPasswordButton);

		parentPanel.add(springPanel, BorderLayout.NORTH);
		parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
		loginFrame.setContentPane(parentPanel);
		loginFrame.pack();
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loginFrame.setLocationRelativeTo(null);
		// The "Login" button automatically listens to the 'Enter' key
		loginFrame.getRootPane().setDefaultButton(loginButton);
		loginFrame.setVisible(true);
	}
}
