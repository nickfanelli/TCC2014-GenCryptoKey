package br.pucc.engComp.GenCryptoKey.views;

import br.pucc.engComp.GenCryptoKey.controller.GenCryptoKey;
import br.pucc.engComp.GenCryptoKey.controller.OneInstanceLock;
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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;

@SuppressWarnings(value = { "serial" })
public class Home extends JFrame {

	private JPanel mainContentPane;
	private JDialog modalDialog;

	private static Home homeFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/**
		 * @@@@@@@@@@@ DEBUG ONLY @@@@@@@@@@@@@@
		 * Uncomment this to bypass window mode
		 */
		//		GenCryptoKey.run();
		//		if(1 != 0) return;
		/**
		 * @@@@@@@@@@@ DEBUG ONLY @@@@@@@@@@@@@@
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
				fileLogout = new JMenuItem("Logout..."),
				fileExit = new JMenuItem("Exit"),
				editUserInfo = new JMenuItem("User info"),
				editSettings = new JMenuItem("Settings"),
				runGenerateKey = new JMenuItem("Generate new key"),
				runGenerateKeyGraphically = new JMenuItem("Generate new key (graphical mode)"),
				viewLastGeneratedKey = new JMenuItem("Last generated key"),
				viewExecutionLog = new JMenuItem("Execution log"),
				helpAbout = new JMenuItem("About CryptoKey");

		try {
			if(UserDAO.getInstance().getUsers().isEmpty()) {
				fileRegisterUser.setEnabled(true);
				fileLogin.setEnabled(false);
			}else{
				fileRegisterUser.setEnabled(false);
				fileLogin.setEnabled(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occured trying to update your user information.", "Database error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error loading user info from database: " + e.getMessage());
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
						viewLastGeneratedKey, viewExecutionLog);
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
				GenCryptoKey.run();
				JLabel generatedKeyMessage = new JLabel("<html><b>Your keypair has been successfully generated!</b><br>Do you wish to view it now?</html>");
				int confirmExportToFile = JOptionPane.showConfirmDialog(null, generatedKeyMessage, "Key generated",
						JOptionPane.YES_NO_OPTION, 1,
						new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png")));
				if(confirmExportToFile == JOptionPane.YES_OPTION) {
					new ViewGeneratedKeys(homeFrame);
				}
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
		viewLastGeneratedKey.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/notesKey24px.png")));
		viewLastGeneratedKey.setEnabled(false);
		viewLastGeneratedKey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewGeneratedKeys(homeFrame);
			}
		});

		viewMenu.add(viewExecutionLog);
		viewExecutionLog.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/notes24px.png")));
		viewExecutionLog.setEnabled(false);


		// Adding 'Help' menu to the menu bar
		menuBar.add(helpMenu);

		helpAbout.setIcon(new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/info24px.png")));
		helpMenu.add(helpAbout);


		mainContentPane = new ImagePanel(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key_DNA_logo-500px.png"));
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPane);

		pack();
		validate();
		repaint();
	}

	// Internal class for dialogs that display messages to the user
	class DialogPanel {
		private final Dimension dialogSize = new Dimension(350, 100);
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
							exportedKeyFile = new PrintWriter("/home/nicholas/testExportKey.txt");
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

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
}
