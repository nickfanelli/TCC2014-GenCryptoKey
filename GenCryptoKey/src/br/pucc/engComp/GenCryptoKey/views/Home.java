package br.pucc.engComp.GenCryptoKey.views;

import br.pucc.engComp.GenCryptoKey.controller.GenCryptoKey;
import br.pucc.engComp.GenCryptoKey.controller.KeypairPOJO;
import br.pucc.engComp.GenCryptoKey.controller.OneInstanceLock;
import br.pucc.engComp.GenCryptoKey.models.KeypairDAO;
import br.pucc.engComp.GenCryptoKey.models.UserDAO;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings(value = { "serial" })
public class Home extends JFrame{

	private JPanel mainContentPane;

	private static Home homeFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/**
		 * @@@@@@@@@@@ DEBUG AND EVALUATION ONLY @@@@@@@@@@@@@@
		 * Uncomment this to bypass window mode
		 */
		//		GenCryptoKey.run(); // Execute regular routine
		//		GenCryptoKey.runEvaluationTests(20); // Execute evaluation routine [number of individuals to be generated and evaluated]
		//		if(1 != 0) return;
		/**
		 * @@@@@@@@@@@ DEBUG AND EVALUATION ONLY @@@@@@@@@@@@@@
		 */

		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setDefaultLookAndFeelDecorated(true);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		/*
		 * Check whether there is already a running instance of the application,
		 * as only 1 should be running at any given moment
		 */
		OneInstanceLock oneInstLock = new OneInstanceLock();

		if (oneInstLock.isGenCryptoKeyAlreadyRunning()) {
			// App is already running
			JOptionPane.showMessageDialog(null, "GenCryptoKey is already running", "Multiple launch prevention", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		else {
			// There are no running instances of the application, thus it may start normally
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					homeFrame = new Home();
					homeFrame.setResizable(false);
					homeFrame.setVisible(true);
				}
			});
		}
	}

	/**
	 * Create the Home frame.
	 */
	public Home() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png")));
		setTitle("GenCryptoKey");
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
				fileLogout = new JMenuItem("Logout..."),
				fileExit = new JMenuItem("Exit"),
				editUserInfo = new JMenuItem("User info"),
				editSettings = new JMenuItem("Settings"),
				runGenerateKey = new JMenuItem("Generate new keypair"),
				runGenerateKeyGraphically = new JMenuItem("Generate new keypair (graphical mode)"),
				viewLastGeneratedKey = new JMenuItem("Last generated keypair"),
				viewGeneratedKeypairsList = new JMenuItem("All generated keypairs list"),
				viewExecutionLog = new JMenuItem("Execution log"),
				helpAbout = new JMenuItem("About GenCryptoKey");

		try {
			if(UserDAO.getUsers().isEmpty()) {
				fileRegisterUser.setEnabled(true);
				fileLogin.setEnabled(false);
			}else{
				fileRegisterUser.setEnabled(false);
				fileLogin.setEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured trying to update your user information.", "Database error", JOptionPane.ERROR_MESSAGE);
			System.out.println("[LOG - ERROR] -- Error loading user info from database: " + e.getMessage());
		}

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
				new Login(homeFrame, fileLogin, fileLogout, editUserInfo, editSettings,
						runGenerateKey, runGenerateKeyGraphically,
						viewLastGeneratedKey, viewGeneratedKeypairsList, viewExecutionLog);
			}
		});

		fileMenu.add(fileRegisterUser);
		fileRegisterUser.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/addUser24px.png")));
		fileRegisterUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				new RegisterUser(homeFrame, fileRegisterUser, fileLogin);
			}
		});

		fileMenu.addSeparator();

		fileMenu.add(fileLogout);
		fileLogout.setEnabled(false);
		fileLogout.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/logout24px.png")));
		fileLogout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				fileLogout.setEnabled(false);
				editUserInfo.setEnabled(false);
				editSettings.setEnabled(false);
				runGenerateKey.setEnabled(false);
				runGenerateKeyGraphically.setEnabled(false);
				viewLastGeneratedKey.setEnabled(false);
				viewExecutionLog.setEnabled(false);
				fileLogin.setEnabled(true);
			}
		});

		fileMenu.add(fileExit);
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
				new EditUser(homeFrame);
			}
		});

		editSettings.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/tools24px.png")));
		editSettings.setEnabled(false);
		editMenu.add(editSettings);
		editSettings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				new GASettings(homeFrame);
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
				final JOptionPane optionPane = new JOptionPane("<html>Please wait...<br>Initializing parameters</html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
						new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/loading.gif")), new Object[]{}, null);

				final JDialog waitingDialog = optionPane.createDialog(optionPane, "GenCryptoKey");
				waitingDialog.setLocationRelativeTo(homeFrame);
				waitingDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


				SwingWorker<Void, Void> backgroundInitThread = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						// Background processing is done here
						System.out.println("[LOG - DEBUG] -- Entrei na execução do initParameters() em background");
						GenCryptoKey.initParameters();
						System.out.println("[LOG - DEBUG] -- Terminei o init.\n");
						return null;
					}

					// This is called when background thread above has completed
					@Override
					protected void done() {
						waitingDialog.setVisible(false);
						optionPane.setMessage("<html>Please wait... <br>Generating new keypair</html>");
					};
				};
				backgroundInitThread.execute();
				waitingDialog.setVisible(true);

				SwingWorker<Void, Void> backgroundGAThread = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						// Background processing is done here
						System.out.println("[LOG - DEBUG] -- Entrei na execução do run() em background");
						GenCryptoKey.run();
						return null;
					}

					// This is called when background thread above has completed
					@Override
					protected void done() {
						waitingDialog.dispose();
					};
				};
				backgroundGAThread.execute();
				waitingDialog.setVisible(true);
			}
		});

		runMenu.add(runGenerateKeyGraphically);
		runGenerateKeyGraphically.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/eye24px.png")));
		runGenerateKeyGraphically.setEnabled(false);
		runGenerateKeyGraphically.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				GenCryptoKey.runGraphically();
				// TODO graphic mode
			}
		});
		runGenerateKeyGraphically.setEnabled(false);

		// Adding 'View' menu to the menu bar
		menuBar.add(viewMenu);

		viewMenu.add(viewLastGeneratedKey);
		viewLastGeneratedKey.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_3.png")));
		viewLastGeneratedKey.setEnabled(false);
		viewLastGeneratedKey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewKeys(homeFrame, null);
			}
		});

		viewMenu.add(viewGeneratedKeypairsList);
		viewGeneratedKeypairsList.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/notesKey24px.png")));
		viewGeneratedKeypairsList.setEnabled(false);
		viewGeneratedKeypairsList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewKeys.getGeneratedKeypairsList();
			}
		});

		viewMenu.add(viewExecutionLog);
		viewExecutionLog.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/notes24px.png")));
		viewExecutionLog.setEnabled(false);


		// Adding 'Help' menu to the menu bar
		menuBar.add(helpMenu);
		helpMenu.add(helpAbout);
		helpAbout.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/info24px.png")));
		helpAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new About(homeFrame);
			}
		});


		mainContentPane = new ImagePanel(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key_DNA_logo-500px.png"));
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPane);

		pack();
		validate();
		repaint();
	}

	// Ask the user whether the previous generation with same key size should be used as input for the generation of the new keypair
	public static boolean queryUserForLastGenerationAsInput() {
		int userChoice = 1;
		JLabel questionToUser = new JLabel("<html><b>The settings you have chosen match those of the last population of the previous keypair generated.</b>" +
				"<br>Do you wish to use such population <u>as input</u> for this next keypair generation?</html>");
		ImageIcon questionIcon = new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/help24px.png"));
		Object[] buttonOptions = {"Yes", "No"};

		System.out.println("\n\n[LOG - DEBUG] -- Abri o dialog\n\n");

		userChoice = JOptionPane.showOptionDialog(null, questionToUser, "Population input choice", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, questionIcon, buttonOptions, buttonOptions[0]);

		System.out.println("\n\n[LOG - DEBUG] -- Fechei o dialog\n\n");
		if(userChoice == 0) return Boolean.TRUE;
		else return Boolean.FALSE;
	}

	// Ask the user to give the generated keypair a descriptive name for future reference
	public static String queryUserForKeypairDescription() {
		JLabel generatedKeyMessage = new JLabel("<html><b>Your keypair has been successfully generated!</b><br>Please give it a descriptive name for future reference.</html>");
		Object[] buttonOptions = {"Save"};
		ImageIcon generatedKeyIcon = new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png"));
		String keypairDescription;

		JPanel panel = new JPanel();
		panel.add(generatedKeyMessage);
		JTextField keypairDescriptionTextField = new JTextField(20);
		panel.add(keypairDescriptionTextField);

		JOptionPane.showOptionDialog(null, panel, "Keypair description", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, generatedKeyIcon, buttonOptions, buttonOptions[0]);

		boolean descriptionOk = true;

		while(true) {
			// Setting the keypair description obtained from user input
			keypairDescription = keypairDescriptionTextField.getText();
			if(keypairDescription.isEmpty()) {
				JOptionPane.showMessageDialog(null, "<html>The keypair description may not be empty. <br>Please give it a descriptive name for future reference.</html>", "Empty description", JOptionPane.ERROR_MESSAGE);
				descriptionOk = false;
			} else {
				try {
					ArrayList<KeypairPOJO> registeredKeypairs = KeypairDAO.getKeypairs();
					for(KeypairPOJO kp : registeredKeypairs) {
						if (keypairDescription.equals(kp.getKeypairDescription())) {
							JOptionPane.showMessageDialog(null, "<html>This description has already been given to another keypair. <br>Please give each keypair an unique descriptive name for future reference.</html>", "Duplicate description name", JOptionPane.ERROR_MESSAGE);
							descriptionOk = false;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(descriptionOk) {
				break;
			} else {
				descriptionOk = true;
				JOptionPane.showOptionDialog(null, panel, "Keypair description", JOptionPane.NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, generatedKeyIcon, buttonOptions, buttonOptions[0]);
			}
		}

		return keypairDescription;
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

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
}
