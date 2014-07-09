package com.doundo.plugin.impl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JToolBar;

import packclient.MainView;

public class PointSizePluginImpl implements ActionListener {

	private static Logger logger = Logger.getLogger(DoUndoPluginImpl.class
			.getName());

	private JToolBar toolbar;

	private JComboBox pointSize;

	private MainView mainview;

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
