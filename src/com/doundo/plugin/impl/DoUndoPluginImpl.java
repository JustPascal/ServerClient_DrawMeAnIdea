package com.doundo.plugin.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JToolBar;

import packclient.MainController;
import packclient.MainView;
import packclient.VoPoint;

public class DoUndoPluginImpl implements Serializable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5632846810847890560L;

	private static Logger logger = Logger.getLogger(DoUndoPluginImpl.class.getName());

	private JToolBar toolbar;

	private MainController drawPanel;

	private JButton undo;

	private JButton redo;

	private List<VoPoint> points;

	private List<VoPoint> pointstoAddorRemove;

	public DoUndoPluginImpl(MainView mainView) {
		logger.info("UndoRedo class Charging");
		this.toolbar = mainView.getToolBar();
		this.drawPanel = mainView.getDrawPanel();
		addButtonsToToolBar();
		initLists();
	}

	private void initLists() {
		points = Collections.synchronizedList(new ArrayList<VoPoint>());
		pointstoAddorRemove = Collections.synchronizedList(new ArrayList<VoPoint>());
	}

	private void addButtonsToToolBar() {
		/* Undo Button */
		undo = new JButton("undo");
		undo.addActionListener(this);
		/* Redo Button */
		redo = new JButton("redo");
		redo.addActionListener(this);
		/* Adding to tool bar */
		toolbar.add(undo);
		toolbar.add(redo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Undo event */
		if (e.getSource().equals(undo)) {
			points = drawPanel.getPoints();
			if (!(drawPanel.getPointsToRemoveorAdd().size() == 0)) {
				pointstoAddorRemove = drawPanel.getPointsToRemoveorAdd();
			}
			for (VoPoint removeThisPoint : pointstoAddorRemove) {
				for (int i = 0; i < points.size(); i++) {
					if (points.get(i).equals(removeThisPoint)) {
						points.remove(i);
					}
				}
			}
			drawPanel.setPointsToRemove();
			drawPanel.setPoints(points);
			drawPanel.repaint();
			undo.setEnabled(false);
			redo.setEnabled(true);
		}
		/* Redo Event */
		if (e.getSource().equals(redo)) {
			points = drawPanel.getPoints();
			for (VoPoint point : pointstoAddorRemove) {
				points.add(point);
			}
			drawPanel.setPoints(points);
			drawPanel.repaint();

			undo.setEnabled(true);
			redo.setEnabled(false);
		}
	}
}
