package com.tools;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import packclient.MainView;

/**
 * Save Image est une classe permettant d'enregistrer une image en affichant une
 * arboresence de dossier
 * 
 * @author pascal
 * 
 */
public class SaveImage {

	private MainView mainView;

	/**
	 * Constructeur saveImage avec en paramètre le MainView
	 * 
	 * @param mainView
	 */
	public SaveImage(MainView mainView) {
		this.mainView = mainView;
	}

	/**
	 * Fonction de sauvegarde
	 */
	public void save() {
		int height = mainView.getDrawPanel().getSize().height;
		int width = mainView.getDrawPanel().getSize().width;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		mainView.getDrawPanel().paint(g);
		g.dispose();
		JFileChooser jfc = new JFileChooser(new File(
				System.getProperty("user.home")));
		if (jfc.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				String extension = checkName(file.getName());
				ImageIO.write(image, extension, file);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(mainView,
						"Le dessin n'a pas pu être enregistrer.");
			}

		}

	}

	/**
	 * Prends en paramètre un string et vérifie que celle-ci a l'extension "png"
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
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
