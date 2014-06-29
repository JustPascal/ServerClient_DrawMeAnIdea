package editeur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import modele.Point;
import packClientEmetteur.Emission;

//CTRL + SHIFT + O pour générer les imports 
public class DrawPanelEmetteur extends JPanel implements Serializable,
		MouseMotionListener, MouseListener {

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
	private int posX = -10, oldX = -10;
	// Position Y du pointeur
	private int posY = -10, oldY = -10;
	// Pour savoir si on doit dessiner ou non
	private boolean erasing = true;
	// Taille du pointeur
	private final int pointerSize = 15;
	// Collection de points !
	private List<Point> points = Collections
			.synchronizedList(new ArrayList<Point>());

	public DrawPanelEmetteur() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

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

	@Override
	public void mouseDragged(MouseEvent e) {
		// On récupère les coordonnées de la souris et on enlève la
		// moitié de la taille du pointeur pour centrer le tracé
		oldX = posX;
		oldY = posY;
		posX = e.getX();
		posY = e.getY();
		points.add(new Point(e.getX() - (pointerSize / 2), e.getY()
				- (pointerSize / 2), pointerSize, pointerColor, pointerType));
		repaint();
		sendToSocket();

	}

	private void sendToSocket() {

		Emission ee = new Emission(points);
		System.out.println("appel de emetteuremission" + points.toString());
		ee.sendToSocketEmission();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		oldX = posX;
		oldY = posY;
		posX = e.getX();
		posY = e.getY();
		points.add(new Point(e.getX() - (pointerSize / 2), e.getY()
				- (pointerSize / 2), pointerSize, pointerColor, pointerType));
		repaint();
		sendToSocket();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}