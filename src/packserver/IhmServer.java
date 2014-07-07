package packserver;

import java.awt.Dimension;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IhmServer extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3904366521353382692L;

	private JTextField texte;
	private JButton connect;
	private JButton deconnect;

	public IhmServer() {
		setTitle("Serveur Draw me an idea !");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(new Dimension(300, 150));

		JPanel panel = new JPanel();

		/*
		 * Form Create Serveur
		 */
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		panel.setLayout(gbl);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridheight = 1;
		JLabel label = new JLabel("Voici l'adresse du serveur. ");
		panel.add(label, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridheight = 1;
		texte = new JTextField(getLocalIpAdresse());
		texte.setEnabled(false);
		panel.add(texte, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridheight = 1;
		connect = new JButton("Connect");
		connect.addActionListener(this);
		panel.add(connect, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridheight = 1;
		deconnect = new JButton("Deconnect");
		deconnect.addActionListener(this);
		panel.add(deconnect, gbc);

		getContentPane().add(panel, BorderLayout.CENTER);
		setVisible(true);
	}

	private String getLocalIpAdresse() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
		}
		return ip;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(connect)) {
			StartServer server = null;
			try {
				server = new StartServer(InetAddress.getLocalHost());
			} catch (UnknownHostException e1) {
				System.out.println("Le serveur n'a pas pu démarrer.");
			}
			connect.setEnabled(false);
		}
		if (e.getSource().equals(deconnect)) {
			JOptionPane.showMessageDialog(this, "Vous quittez le serveur.");
			System.exit(0);
		}

	}

}
