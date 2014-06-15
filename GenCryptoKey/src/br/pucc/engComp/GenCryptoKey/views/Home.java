package br.pucc.engComp.GenCryptoKey.views;

import br.pucc.engComp.GenCryptoKey.controller.GenCryptoKey;
import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dialog.ModalityType;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@SuppressWarnings(value = { "serial" })
public class Home extends JFrame {

	private JPanel mainContentPane;
	private JDialog modalDialog;
	
	static final Home homeFrame = new Home();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					homeFrame.setResizable(false);
					homeFrame.setVisible(true);
			}
		});
	}

	/**
	 * Create the Home frame.
	 */
	public Home() {
		setDefaultLookAndFeelDecorated(true);
		//setIconImage(new ImageIcon("/br/pucc/engComp/GenCryptoKey/resources//media/nicholas/UUI/TCC/TCC2014-GenCryptoKey/Icons_24px/clock24px.png").getImage());
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png")));
		setTitle("CryptoKey");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// Menu objects
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File"), 
				editMenu = new JMenu("Edit"),
				runMenu = new JMenu("Run"),
				viewMenu = new JMenu("View"),
				helpMenu = new JMenu("Help");
		final JMenuItem fileLogin = new JMenuItem("Login..."),
						fileRegisterUser = new JMenuItem("Register User"),
						fileExit = new JMenuItem("Exit"),
						editUserInfo = new JMenuItem("User info"),
						editSettings = new JMenuItem("Settings"),
						runGenerateKey = new JMenuItem("Generate new key"),
						runGenerateKeyGraphically = new JMenuItem("Generate new key (graphical mode)"),
						viewLastGeneratedKey = new JMenuItem("Last generated key"),
						viewExecutionLog = new JMenuItem("Execution log"),
						helpAbout = new JMenuItem("About CryptoKey");
		
		// Instantiates the menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Adding File menu to the menu bar
		menuBar.add(fileMenu);
		
		fileMenu.add(fileLogin);
		fileLogin.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/login24px.png")));
		fileLogin.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			final JFrame loginFrame = new JFrame("Login");
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
    	    			// TODO Check database for registered user
    	    			// If user info matches, set userLoggedIn
    	    			// to enable session
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
									loginFrame.dispose();
								}else {
									// TODO warn user of incorrect info
									System.out.println("Usernamed and/or password are incorrect.");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
    						
    					}
    					editUserInfo.setEnabled(true);
    	    			editSettings.setEnabled(true);
    					runGenerateKey.setEnabled(true);
    					//runGenerateKeyGraphically.setEnabled(true);
    	    			//viewLastGeneratedKey.setEnabled(true);
    	    			//viewExecutionLog.setEnabled(true);
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
    	});
		
		fileMenu.add(fileRegisterUser);
		fileRegisterUser.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/addUser24px.png")));
		fileRegisterUser.addActionListener(new ActionListener(){
			@Override			
    		public void actionPerformed(ActionEvent evt) {
    			final JFrame registerUserFrame = new JFrame("Register user");
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
    	    	
    	    	JButton registerButton = new JButton("Register");
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
    	});
		
		fileMenu.addSeparator();
		fileMenu.add(fileExit);
		fileExit.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/eject24px.png")));
		fileExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				homeFrame.dispose();
				
			}
			
		});
		// Adding Edit menu to the menu bar
		menuBar.add(editMenu);
		
		editMenu.add(editUserInfo);
		editUserInfo.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/editPencil24px.png")));
		editUserInfo.setEnabled(false);
		editUserInfo.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			final JFrame editUserFrame = new JFrame("Edit user info");
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
    	});
		
		editSettings.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/tools24px.png")));
		editSettings.setEnabled(false);
		editMenu.add(editSettings);
		editSettings.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			GASettings frame = new GASettings();
				frame.setVisible(true);
            }
    	});
		
		// Adding 'Run' menu to the menu bar
		menuBar.add(runMenu);
		
		runMenu.add(runGenerateKey);
		runGenerateKey.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px.png")));
		runGenerateKey.setEnabled(false);
		runGenerateKey.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			GenCryptoKey.run();
    			JLabel generatedKey = new JLabel("Key generated!");
    			openMessageDialog(generatedKey, "Export to file");
            }
    	});
		
		runMenu.add(runGenerateKeyGraphically);
		runGenerateKeyGraphically.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/eye24px.png")));
		runGenerateKeyGraphically.setEnabled(false);
		runGenerateKeyGraphically.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent evt) {
    			GenCryptoKey.runGraphically();
    			// TODO
            }
    	});
		runGenerateKeyGraphically.setEnabled(false);
		
		// Adding 'View' menu to the menu bar
		menuBar.add(viewMenu);
		
		viewLastGeneratedKey.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/notesKey24px.png")));
		viewLastGeneratedKey.setEnabled(false);
		viewMenu.add(viewLastGeneratedKey);
		
		viewExecutionLog.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/notes24px.png")));
		viewExecutionLog.setEnabled(false);
		viewMenu.add(viewExecutionLog);
		
		// Adding 'Help' menu to the menu bar
		menuBar.add(helpMenu);
		
		helpAbout.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/help24px.png")));
		helpMenu.add(helpAbout);
		
		
		mainContentPane = new ImagePanel(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key_DNA_logo-500px.png"));
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPane);
			
		pack();
		validate();
		repaint();		
	}
	
	private void openMessageDialog(JLabel label, String newCustomButton) {
		if(modalDialog == null){
			modalDialog = new JDialog(homeFrame, "Output", ModalityType.APPLICATION_MODAL);
			modalDialog.getContentPane().add(new DialogPanel(label, newCustomButton).getMainPanel());
			modalDialog.pack();
			modalDialog.setLocationRelativeTo(homeFrame);
			modalDialog.setResizable(false);
			modalDialog.setVisible(true);
		}else {
			modalDialog.setVisible(true);
		}
	}
	
	// Internal class for dialogs that display messages to the user
	class DialogPanel {
		private final Dimension dialogSize = new Dimension(350, 75);
		private JPanel dialogPanel = new JPanel();
		
		public DialogPanel() {
			JButton buttonOk = new JButton("OK");
			buttonOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					modalDialog.dispose();
				}
			});
			dialogPanel.add(buttonOk);
			dialogPanel.setPreferredSize(dialogSize);
		}
		
		public DialogPanel(JLabel dialogLabel, String newCustomButton) {
			dialogPanel.add(dialogLabel);
			JButton buttonOk = new JButton("OK");
			JButton customButton = null;
			if(!newCustomButton.isEmpty()){
				customButton = new JButton(newCustomButton);
				customButton.addActionListener(new ActionListener(){
					@Override
		    		public void actionPerformed(ActionEvent evt) {
		    			PrintWriter exportedKeyFile = null;
		    			try {
		    				exportedKeyFile = new PrintWriter("/home/nicholas/testExportkey.txt");
		    			}catch(FileNotFoundException fnfe) {
		    				fnfe.printStackTrace();
		    			}
		    			exportedKeyFile.println(GenCryptoKey.getGeneratedKey());
		    			exportedKeyFile.close();
		            }
		    	});
			}
			buttonOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					modalDialog.dispose();
				}
			});
			dialogPanel.add(buttonOk);
			dialogPanel.add(customButton);
			dialogPanel.setPreferredSize(dialogSize);
		}
		
		public JPanel getMainPanel() {
			return dialogPanel;
		}
	}
	
	// Internal class used to set the logo image on the Home window
	//@SuppressWarnings(value = { "serial" })
	class ImagePanel extends JPanel {

		private Image img;

		  public ImagePanel(URL img) {
		    this(new ImageIcon(img).getImage());
		  }

		  public ImagePanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }
	}
}
