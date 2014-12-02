package br.pucc.engComp.GenCryptoKey.views;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings(value = { "serial" })
public class About extends JFrame{

	public About(JFrame homeFrame){
		String version = "1.0.0";
		String aboutMessage =
				"<html> <BR>"
						+ "<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
						+ "GenCryptoKey</h1>"
						+ "<h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "<b>Version: " + version + "</b></h4> <BR>"
						+ "<b>This program was developed as part of the Final Project discipline of the Computer Engineering <BR>"
						+ "curriculum of the Pontifical Catholic University of Campinas.</b> <BR><BR>"
						+ "Developed by Nicholas Fanelli <BR>"
						+ "(nicholas.bffh@puccampinas.edu.br / nicholashugueney@gmail.com) <BR><BR>"
						+ "Orienting professor: Dr. Carlos Miguel Tobar Toledo <BR>"
						+ "(tobar@puc-campinas.edu.br) <BR><BR><BR>"
						+ "The GenCryptoKey logo cannot be altered without written permission of the developer. <BR>"
						+ "This application contains software developed by other open source projects, <BR>"
						+ "including Oracle (http://www.oracle.com/) and Apache Software Foundation (http://www.apache.org/) <BR><BR><BR><BR>"
						+ "<hr>" // line divider
						+ "Copyright (c) Nicholas Fanelli 2014. "
						+ "Copy and redistribution of this application is permitted under<BR>"
						+ "the GNU Public License version 2 (GPLv2) provided due credits are given." +
				"</html>";

		//JOptionPane.showMessageDialog(homeFrame, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
		JButton okButton = new JButton("Ok");
		final JOptionPane optionPane = new JOptionPane(aboutMessage, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key_DNA_logo-96px-no_bg.png")), new Object[]{okButton}, okButton);

		final JDialog aboutDialog = optionPane.createDialog(optionPane, "About");
		aboutDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key_DNA_logo-24px.png")));

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aboutDialog.dispose();
			}
		});

		aboutDialog.setVisible(true);
	}
}
