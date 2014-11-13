package br.pucc.engComp.GenCryptoKey.views;

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
                "<html>" +
                        "<BR>" +
                            "<b>This program has been developed as part of the Computer Engineering final project - 2014</b>" +
                            "<BR>" +
                            "<BR>" +
                                "Developer: Nicholas Fanelli Hugueney - RA: 09096646" +
                            "<BR>" +
                                "Orienting professor: Dr. Carlos Miguel Tobar Toledo" +
                            "<BR>" +
                            "<BR>" +
                                "Version: " + version +
                "</html>";
        
        //JOptionPane.showMessageDialog(homeFrame, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
        JButton okButton = new JButton("Ok");
        final JOptionPane optionPane = new JOptionPane(aboutMessage, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
                new ImageIcon(Home.class.getResource("/br/pucc/engComp/GenCryptoKey/resources/key24px_2.png")), new Object[]{okButton}, okButton);

        final JDialog aboutDialog = optionPane.createDialog(optionPane, "About");
        
        okButton.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent arg0) {
                aboutDialog.dispose();
            }
        });
        
        aboutDialog.setVisible(true);
    }
}
