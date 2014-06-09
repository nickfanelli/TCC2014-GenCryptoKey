package br.pucc.engComp.GenCryptoKey.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings(value = { "serial" })
public class Home extends JFrame {

	private JPanel mainContentPane;
	private boolean userLoggedIn = true;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key.png")));
		setTitle("CryptoKey");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// File Menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem fileLogin = new JMenuItem("Login...");
		fileMenu.add(fileLogin);
		fileLogin.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			final JFrame loginFrame = new JFrame("Login");
    	    	String[] loginLabels = {"Username: ", "Password: "};
    	    	int numLabels = loginLabels.length;
    	    	
    	    	ArrayList<JTextField> valueFields = new ArrayList<JTextField>();
    	    	
    	    	JPanel parentPanel = new JPanel(new BorderLayout());
    	    	
    	    	// Formates the panel and creates the grid
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
    	    	
    	    	// Formates the panel and creates the grid
    	    	SpringUtilities.makeCompactGrid(springPanel,
    	    	                                numLabels, 2, // lines, columns
    	    	                                6, 6,        // initX, initY
    	    	                                6, 6);       // xPad, yPad
    	    	
    	    	
    	    	JPanel buttonsPanel = new JPanel();
    	    	buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
    	    	
    	    	JButton loginButton = new JButton("Log In");
    	    	loginButton.addActionListener(new ActionListener(){
    				@Override
    	    		public void actionPerformed(ActionEvent e) {
    	    			// TODO Check database for registered user
    	    			// If user info matches, set userLoggedIn
    	    			// to enable session
    	    			userLoggedIn = true;
    	            }
    	    	});
    	    	JButton cancelButton = new JButton("Cancel");
    	    	cancelButton.addActionListener(new ActionListener(){
    				@Override
    	    		public void actionPerformed(ActionEvent e) {
    	    			loginFrame.dispose();
    	            }
    	    	});
    	    	JButton resetPasswordButton = new JButton("Forgot my password");
    	    	resetPasswordButton.addActionListener(new ActionListener(){
    				@Override
    	    		public void actionPerformed(ActionEvent e) {
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
		
		JMenuItem fileRegisterUser= new JMenuItem("Register user");
		fileMenu.add(fileRegisterUser);
		fileRegisterUser.addActionListener(new ActionListener(){
			@Override			
    		public void actionPerformed(ActionEvent e) {
    			final JFrame registerUserFrame = new JFrame("Register user");
    			String[] loginLabels = {"First name: ", "Last name: ", "E-Mail: ", "Confirm E-Mail: ",
    									"Username: ", "Password: ", "Confirm password: "};
    	    	int numLabels = loginLabels.length;
    	    	
    	    	final ArrayList<JTextField> valueFields = new ArrayList<JTextField>();
    	    	
    	    	JPanel parentPanel = new JPanel(new BorderLayout());
    	    	
    	    	// Cria e popula o painel para o grid
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
    	    	
    	    	// Formates the panel and creates the grid
    	    	SpringUtilities.makeCompactGrid(springPanel,
    	    	                                numLabels, 2, // lines, columns
    	    	                                6, 6,        // initX, initY
    	    	                                6, 6);       // xPad, yPad
    	    	
    	    	
    	    	JPanel buttonsPanel = new JPanel();
    	    	buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
    	    	
    	    	JButton registerButton = new JButton("Register");
    	    	registerButton.addActionListener(new ActionListener(){
    				@Override
    	    		public void actionPerformed(ActionEvent e) {
    	    			// TODO Register user
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
    	    		public void actionPerformed(ActionEvent e) {
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
		
		JMenuItem fileExit = new JMenuItem("Exit");
		fileMenu.add(fileExit);
		fileExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				homeFrame.dispose();
				
			}
			
		});
		// Edit Menu
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenuItem mntmUserInfo = new JMenuItem("User Info");
		editMenu.add(mntmUserInfo);
		mntmUserInfo.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			final JFrame editUserFrame = new JFrame("Edit user info");
    			String[] loginLabels = {"First name: ", "Last name: ", "E-Mail: ", "Confirm E-Mail: ",
    									"Username: ", "Password: ", "Confirm password: "};
    	    	int numLabels = loginLabels.length;
    	    	
    	    	final ArrayList<JTextField> valueFields = new ArrayList<JTextField>();
    	    	
    	    	JPanel parentPanel = new JPanel(new BorderLayout());
    	    	
    	    	// Formates the panel and creates the grid
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
    	    	
    	    	// Formates the panel and creates the grid
    	    	SpringUtilities.makeCompactGrid(springPanel,
    	    	                                numLabels, 2, // lines, columns
    	    	                                6, 6,        // initX, initY
    	    	                                6, 6);       // xPad, yPad
    	    	
    	    	// TODO Recover user info from database and set fields
    	    	
    	    	JPanel buttonsPanel = new JPanel();
    	    	buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
    	    	
    	    	JButton registerButton = new JButton("Save changes");
    	    	registerButton.addActionListener(new ActionListener(){
    				@Override
    	    		public void actionPerformed(ActionEvent e) {
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
    	    		public void actionPerformed(ActionEvent e) {
    					editUserFrame.dispose();
    	            }
    	    	});
    	    	
    	    	buttonsPanel.add(registerButton);
    	    	buttonsPanel.add(cancelButton);
    	    	
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
		
		JMenuItem editSettings = new JMenuItem("Settings");
		if(userLoggedIn){
			editSettings.setEnabled(true);
		}else{
			editSettings.setEnabled(false);
		}
		
		editSettings.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/settingsWrench.png")));
		editMenu.add(editSettings);
		editSettings.addActionListener(new ActionListener(){
			@Override
    		public void actionPerformed(ActionEvent e) {
    			GASettings frame = new GASettings();
				frame.setVisible(true);
            }
    	});
		
		// View Menu
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmGraphicExecution = new JMenuItem("Graphic execution");
		mntmGraphicExecution.setEnabled(false);
		mnView.add(mntmGraphicExecution);
		
		JMenuItem mntmLastGeneratedKey = new JMenuItem("Last generated key");
		mntmLastGeneratedKey.setEnabled(false);
		mnView.add(mntmLastGeneratedKey);
		
		JMenuItem mntmExecutionLog = new JMenuItem("Execution log");
		mntmExecutionLog.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/document.png")));
		mntmExecutionLog.setEnabled(false);
		mnView.add(mntmExecutionLog);
		
		// Help Menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem helpAbout = new JMenuItem("About CryptoKey");
		helpAbout.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/info.png")));
		helpMenu.add(helpAbout);
		
		mainContentPane = new ImagePanel(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/Key_DNA_logo-500px.png"));
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPane);
			
		pack();
		validate();
		repaint();		
	}
	
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
