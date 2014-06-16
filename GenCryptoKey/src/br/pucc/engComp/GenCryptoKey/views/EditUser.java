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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;

@SuppressWarnings(value = { "serial" })
public class EditUser extends JFrame{
	
	public EditUser(JFrame homeFrame){
		final JDialog editUserFrame = new JDialog(homeFrame, "Edit user info", true);
		String[] loginLabels = {"First name: ", "Last name: ", "E-Mail: ", "Confirm E-Mail: ",
								"Username: ", "Password: ", "Confirm password: "};
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
    	
    	// TODO Recover user info from database and set fields
    	try {
			ArrayList<UserPOJO> registeredUser = UserDAO.getInstance().getUsers();
			valueFields.get(0).setText(registeredUser.get(0).getFirstName());
			valueFields.get(1).setText(registeredUser.get(0).getLastName());
			valueFields.get(2).setText(registeredUser.get(0).getEmail());
			valueFields.get(3).setText(registeredUser.get(0).getEmail());
			valueFields.get(4).setText(registeredUser.get(0).getUsername());
			valueFields.get(4).setEnabled(false);
			valueFields.get(5).setText(registeredUser.get(0).getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	JPanel buttonsPanel = new JPanel();
    	buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
    	
    	JButton registerButton = new JButton("Save changes");
    	registerButton.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			// TODO Update user info
    			// Verify no field is empty before proceeding
    			if(valueFields.get(0).getText().isEmpty()
    					|| valueFields.get(1).getText().isEmpty()
    					|| valueFields.get(2).getText().isEmpty()
    					|| valueFields.get(3).getText().isEmpty()
    					|| valueFields.get(4).getText().isEmpty()
    					|| valueFields.get(5).getText().isEmpty()
    					|| valueFields.get(6).getText().isEmpty()){
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
				editUserFrame.dispose();
            }
    	});
    	
    	buttonsPanel.add(registerButton);
    	buttonsPanel.add(cancelButton);
    	buttonsPanel.add(deleteUserButton);
    	
    	parentPanel.add(springPanel, BorderLayout.NORTH);
    	parentPanel.add(buttonsPanel, BorderLayout.SOUTH);
    	editUserFrame.setContentPane(parentPanel);
    	editUserFrame.pack();
    	editUserFrame.setResizable(false);
    	editUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	editUserFrame.setLocationRelativeTo(null);
    	editUserFrame.setVisible(true);
	}

}
