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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;

@SuppressWarnings(value = { "serial" })
public class RegisterUser extends JFrame{
	
	public RegisterUser(JFrame homeFrame, final JMenuItem fileRegisterUserMenu){
		final JDialog registerUserFrame = new JDialog(homeFrame, "Register user", true);
		String[] loginLabels = {"First name: ", "Last name: ", "E-Mail: ", "Confirm E-Mail: ",
								"Username: ", "Password: ", "Confirm password: "};
    	int numLabels = loginLabels.length;
    	
    	final ArrayList<JTextField> valueFields = new ArrayList<JTextField>();
    	
    	JPanel parentPanel = new JPanel(new BorderLayout());
    	
    	// Creates and populates the panel for the grid
    	JPanel springPanel = new JPanel(new SpringLayout());
    	springPanel.setOpaque(true);
    	
    	for (int i = 0; i < numLabels; i++) {
    	    JLabel l = new JLabel(loginLabels[i], JLabel.TRAILING);
    	    springPanel.add(l);
    	    if(l.getText().toLowerCase().contains("password")){
    	    	JPasswordField passwordTextField = new JPasswordField(20);
    	    	l.setLabelFor(passwordTextField);
	    	    springPanel.add(passwordTextField);
	    	    valueFields.add(passwordTextField);
    	    }else{
    	    	JTextField userInfologinTextField = new JTextField(20);
    	    	l.setLabelFor(userInfologinTextField);
	    	    springPanel.add(userInfologinTextField);
	    	    valueFields.add(userInfologinTextField);
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
    			// TODO Register user
    			// Verify no field is empty before proceeding
    			if(valueFields.get(0).getText().isEmpty()
    					|| valueFields.get(1).getText().isEmpty()
    					|| valueFields.get(2).getText().isEmpty()
    					|| valueFields.get(3).getText().isEmpty()
    					|| valueFields.get(4).getText().isEmpty()
    					|| valueFields.get(5).getText().isEmpty()
    					|| valueFields.get(6).getText().isEmpty()) {
    				// One or more fields are empty
    				// warn user
    				
    			}else if(!valueFields.get(2).getText().equals(valueFields.get(3).getText())){
	    				// E-mail & E-Mail confirmation fields have different values 
	    				// warn user
	    			}else if(!valueFields.get(5).getText().equals(valueFields.get(6).getText())){
	    				// Password & Password confirmation fields have different values
	    				// warn user
	    			}else{
	    				// If more than 1 user is allowed
	    				//Check if Username and or E-Mail isn't already in use
	    				UserPOJO newUser = new UserPOJO();
	    				newUser.setFirstName(valueFields.get(0).getText());
	    				newUser.setLastName(valueFields.get(1).getText());        	    				
	    				newUser.setEmail(valueFields.get(2).getText());
	    				newUser.setUsername(valueFields.get(4).getText());
	    				newUser.setPassword(valueFields.get(5).getText());
	    				
	    				try {
							if(UserDAO.getInstance().newUser(newUser) != -1) {
								System.out.println("User successfully registered.");
								registerButton.setEnabled(false);
								fileRegisterUserMenu.setEnabled(false);
								registerUserFrame.dispose();
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
    	registerUserFrame.setVisible(true);
	}

}
