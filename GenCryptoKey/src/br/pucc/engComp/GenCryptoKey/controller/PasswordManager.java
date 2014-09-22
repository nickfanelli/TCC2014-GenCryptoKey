package br.pucc.engComp.GenCryptoKey.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import br.pucc.engComp.GenCryptoKey.models.UserDAO;
import br.pucc.engComp.GenCryptoKey.views.Home;

import com.sun.mail.smtp.SMTPTransport;

public class PasswordManager {

	public PasswordManager() {
	}

	public String calculateSha256HashString(String stringToHash) {
		StringBuffer sha256HashString = new StringBuffer();

		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
			byte[] stringToHashByteArray = mDigest.digest(stringToHash.getBytes());
			for (int i = 0; i < stringToHashByteArray.length; i++) {
				sha256HashString.append(Integer.toString((stringToHashByteArray[i] & 0xff) + 0x100, 16).substring(1));
			}
		}catch(NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}

		return sha256HashString.toString();
	}

	public String generateBackupPassword() {
		return new BigInteger(130, new SecureRandom()).toString(30);
	}

	public void sendPasswordRecoveryEmail() {

		final JOptionPane optionPane = new JOptionPane("Please wait...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/loading.gif")), new Object[]{}, null);

		final JDialog dialog = optionPane.createDialog(optionPane, "Sending e-mail");

		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// Background processing is done here

				Properties props = System.getProperties();
				props.setProperty("mail.smtps.host", "smtp.gmail.com");
				props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.setProperty("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.port", "465");
				props.setProperty("mail.smtp.socketFactory.port", "465");
				props.setProperty("mail.smtps.auth", "true");
				props.put("mail.smtps.quitwait", "false");

				Session session = Session.getInstance(props, null);

				final MimeMessage msg = new MimeMessage(session);

				try{
					// Recover user's backup password from database to send through e-mail
					String userFirstName = null, userEmail = null, userBackupPassword = null;
					ArrayList<UserPOJO> registeredUser = UserDAO.getInstance().getUsers();

					if(registeredUser != null && !registeredUser.isEmpty()) {
						userFirstName = registeredUser.get(0).getFirstName();
						userEmail = registeredUser.get(0).getEmail();
						userBackupPassword = registeredUser.get(0).getBackupPassword();
					}

					msg.setFrom(new InternetAddress("cinemasvoid@gmail.com"));
					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));

					msg.setSubject("CryptoKey | Password recovery");
					msg.setText("## THIS IS AN AUTOMATIC MESSAGE! Please do not reply ##\n\n"
							+ userFirstName + ", it appears you have requested the reset of your CryptoKey application password.\n\n"
							+ "To do so, please log in using your username and the code provided below:\n\n"
							+ "\t" + userBackupPassword + "\n\n"
							+ "After that, it is strongly recommended that you edit your personal information and update your password.\n\n\n"
							+ "In the case this was not a legitimate request, however, you may simply ignore this message." + "\n\n", "utf-8");
					msg.setSentDate(new Date());

					SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

					t.connect("smtp.gmail.com", "cinemasvoid", "void1234");
					t.sendMessage(msg, msg.getAllRecipients());
					t.close();

				}catch(Exception e){
					e.printStackTrace();
				}

				System.out.println("Password recovery e-mail was successfully sent.");

				return null;
			}

			// this is called when background thread above has completed.
			@Override
			protected void done() {
				dialog.dispose();
			};
		}.execute();

		dialog.setVisible(true);
	}
}
