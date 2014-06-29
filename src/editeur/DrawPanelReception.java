package editeur;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import modele.Point;

//CTRL + SHIFT + O pour générer les imports 
public class DrawPanelReception extends JPanel {

	@Override
	public String toString() {
		return "DrawPanel [pointerColor=" + pointerColor + ", pointerType="
				+ pointerType + ", posX=" + posX + ", oldX=" + oldX + ", posY="
				+ posY + ", oldY=" + oldY + ", erasing=" + erasing
				+ ", pointerSize=" + pointerSize + ", points="
				+ points.toString() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7585082323280766027L;
	// Couleur du pointeur
	private Color pointerColor = Color.red;
	// Forme du pointeur
	private String pointerType = "CIRCLE";
	// Position X du pointeur
	private final int posX = -10, oldX = -10;
	// Position Y du pointeur
	private final int posY = -10, oldY = -10;
	// Pour savoir si on doit dessiner ou non
	private boolean erasing = true;
	// Taille du pointeur
	private final int pointerSize = 15;
	// Collection de points !
	private List<Point> points = Collections
			.synchronizedList(new ArrayList<Point>());

	public DrawPanelReception() {

	}

	// Vous la connaissez maintenant, celle-là
	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Si on doit effacer, on ne passe pas dans le else => pas de dessin
		if (this.erasing) {
			this.erasing = false;
		} else {
			// On parcourt notre collection de points
			for (Point p : this.points) {
				// On récupère la couleur
				g.setColor(p.getColor());

				// Selon le type de point
				if (p.getType().equals("SQUARE")) {
					g.fillRect(p.getX(), p.getY(), p.getSize(), p.getSize());
				} else {
					g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
				}
			}
		}
	}

	// Efface le contenu
	public void erase() {
		this.erasing = true;
		this.points = Collections.synchronizedList(new ArrayList<Point>());
		repaint();
	}

	// Définit la couleur du pointeur
	public void setPointerColor(Color c) {
		this.pointerColor = c;
	}

	// Définit la forme du pointeur
	public void setPointerType(String str) {
		this.pointerType = str;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

}