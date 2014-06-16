package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;

@SuppressWarnings(value = { "serial" })
public class Login extends JFrame{
	
	public Login(JFrame homeFrame, final JMenuItem editUserInfoMenu, final JMenuItem editSettingsMenu,
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
					
					// Calculate SHA-1 hash of the user's input password
					StringBuffer sb = new StringBuffer();
					try {
						MessageDigest mDigest = MessageDigest.getInstance("SHA1");
						byte[] result = mDigest.digest(valueFields.get(1).getText().getBytes());
						for (int i = 0; i < result.length; i++) {
							sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
						}
					}catch(NoSuchAlgorithmException nsae) {
						nsae.printStackTrace();
					}
					
					user.setPassword((valueFields.get(1).getText()));
					try {
						if(UserDAO.getInstance().isRegistered(user)) {
							System.out.println("User successfully logged in.");
							editUserInfoMenu.setEnabled(true);
	    	    			editSettingsMenu.setEnabled(true);
	    					runGenerateKeyMenu.setEnabled(true);
	    					// TODO Set to true once these functionalities are implemented.
	    					runGenerateKeyGraphicallyMenu.setEnabled(false);
	    	    			viewLastGeneratedKeyMenu.setEnabled(false);
	    	    			viewExecutionLogMenu.setEnabled(false);
							loginFrame.dispose();
						}else {
							// TODO warn user of incorrect info
							System.out.println("Usernamed and/or password are incorrect.");
						}
					} catch (Exception e) {
						e.printStackTrace();
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
    			// TODO Reset user password logic
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
    	loginFrame.setVisible(true);
	}
}
