package com.tools;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import packClient.MainView;

public class MainViewWindowListener implements WindowListener {

	private MainView mainView;

	public MainViewWindowListener(MainView mainview) {
		this.mainView = mainview;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		JOptionPane.showMessageDialog(mainView, "Launched False");
		mainView.setLaunched(false);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int rep = JOptionPane.showConfirmDialog(null, "Veux tu quitter l'application ?");
		if (rep == 0)
			System.exit(0);
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