package com.saveimage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import packClient.MainView;

public class SaveImage {

	private MainView mainView;

	public SaveImage(MainView mainView) {
		this.mainView = mainView;
	}

	public void save() {
		int height = mainView.getDrawPanel().getSize().height;
		int width = mainView.getDrawPanel().getSize().width;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		mainView.getDrawPanel().paint(g);
		g.dispose();
		JFileChooser jfc = new JFileChooser(
				new File(System.getProperty("user.home")));
		if (jfc.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				String extension = checkName(file.getName());
				ImageIO.write(image, extension, file);
			} catch (Exception e) {
			}

		}

	}

	public String checkName(String name) throws Exception {
		if (name.endsWith(".png") || name.endsWith(".PNG"))
			return "png";
		else {
			JOptionPane.showMessageDialog(mainView,
					"L'extension de l'image doit être de type .png.");
			throw new Exception("L'extension de l'image est mauvaise.");
		}
	}

}
