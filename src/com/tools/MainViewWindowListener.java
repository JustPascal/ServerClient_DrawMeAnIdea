package com.tools;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

/**
 * Class MainviewWindowListener qui nous permet de traiter les actions qui ont
 * lieu sur la fenêtre
 * 
 * @author pascal
 * 
 */
public class MainViewWindowListener implements WindowListener {

	/**
	 * Constructeur qui prend en paramètre le mainView
	 * 
	 */
	public MainViewWindowListener() {
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int rep = JOptionPane.showConfirmDialog(null,
				"Veux tu quitter l'application ?");
		if (rep == 0) {
			System.exit(0);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}