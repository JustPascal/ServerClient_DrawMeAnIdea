package com.doundo.plugin.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JToolBar;

import packclient.MainView;

/**
 * Plugin permettant d'implementer un combobox pour que l'utilisateur puisse
 * changer la taille de son stylo
 * 
 * @author pascal
 * 
 */
public class PointSizePluginImpl implements ActionListener {

	private static Logger logger = Logger.getLogger(DoUndoPluginImpl.class
			.getName());

	private JToolBar toolbar;

	private JComboBox pointSize;

	private MainView mainview;

	/**
	 * Constructeur avec un mainview en parametre afin d'avoir accès au toolbar
	 * et y ajouter le combobox
	 * 
	 * @param mainView
	 */
	public PointSizePluginImpl(MainView mainView) {
		logger.info("Point Size Plugin class Charging");
		this.mainview = mainView;
		this.toolbar = mainView.getToolBar();
		initDotLineSize();
		toolbar.add(pointSize);
	}

	private void initDotLineSize() {
		// size of the depth of the dot or line
		pointSize = new JComboBox();
		// pointSize.setPreferredSize(new Dimension(30, 30));
		// pointSize.setSize(new Dimension(30, 30));
		// pointSize.setMaximumSize(pointSize.getPreferredSize());

		pointSize.addActionListener(this);
		int i;
		for (i = 1; i <= 50; i++) {
			pointSize.addItem(i);
		}
		pointSize.setSelectedItem(15);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(pointSize)) {
			mainview.getDrawPanel().setPointerSize(
					(Integer) pointSize.getSelectedItem());
		}

	}
}
