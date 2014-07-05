package com.aboutframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AboutFrame extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1559958482435523731L;

	private static final String APPLI_NAME = "Draw Me An Idea";

	private static final Dimension DIMENSION = new Dimension(240, 240);

	private JButton bClose;

	public AboutFrame() {
		guiFactory();
	}

	private void guiFactory() {
		final Container c = getContentPane();

		final JPanel containerPanel = new JPanel(new BorderLayout());

		final JLabel appliName = new JLabel(APPLI_NAME, new ImageIcon(getClass().getClassLoader().getResource("resource/Logo-empoule.png")), SwingConstants.CENTER);
		final JLabel appliInfo = new JLabel(
				"<html>Draw me an Idea est une application de dessin en temps r�elle cr�e par Yossi Attia et Pascal Niyitegeka.</html>",
				SwingConstants.CENTER);
		appliInfo.setPreferredSize(new Dimension(230, 60));

		final JPanel panelSouth = new JPanel(new FlowLayout());
		final JPanel panelMiddle = new JPanel();
		panelMiddle.add(appliInfo);
		bClose = new JButton("Quitter");
		bClose.addActionListener(this);
		panelSouth.add(bClose);

		panelSouth.setBackground(Color.white);
		panelSouth.setOpaque(true);

		containerPanel.add(appliName, BorderLayout.NORTH);
		containerPanel.add(panelMiddle, BorderLayout.CENTER);
		containerPanel.add(panelSouth, BorderLayout.SOUTH);
		containerPanel.setBackground(Color.white);
		containerPanel.setOpaque(true);
		containerPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

		c.add(containerPanel);
		setModal(true);
		setSize(DIMENSION);
		setUndecorated(true);
		centerLocation();
	}

	private void centerLocation() {
		final Dimension dim = getToolkit().getScreenSize();
		final Rectangle bounds = getBounds();
		setLocation((dim.width - bounds.width) / 2,
				(dim.height - bounds.width) / 2);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
	}

}
