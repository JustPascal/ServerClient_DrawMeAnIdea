package packclient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class MianController extends JPanel implements Serializable,
		MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2250985983378564880L;

	private String pointerType = "CIRCLE";

	private Color pointerColor = Color.red;

	private int posX = -10, oldX = -10;

	// Position Y du pointeur
	private int posY = -10, oldY = -10;

	// Pour savoir si on doit dessiner ou non
	private boolean erasing = true;

	// Taille du pointeur
	private int pointerSize = 15;

	// Collection de points !
	private List<VoPoint> points = Collections
			.synchronizedList(new ArrayList<VoPoint>());

	/* Points to remove */
	private List<VoPoint> pointsToRemove = Collections
			.synchronizedList(new ArrayList<VoPoint>());

	/* -- -- -- -- -- -- */
	public boolean recepteur = true;

	public InetAddress ipRecepteur = null;

	public Socket socketEmission = null;

	public Socket getSocketEmission() {
		return socketEmission;
	}

	public void setSocketEmission(Socket socketEmission) {
		this.socketEmission = socketEmission;
	}

	public InetAddress getIpRecepteur() {
		return ipRecepteur;
	}

	public boolean setIpRecepteur(InetAddress ipRecepteur) {
		this.ipRecepteur = ipRecepteur;

		try {

			socketEmission = new Socket(ipRecepteur, 4184);
			return true;
		} catch (ConnectException e) {
			System.out.println("Client recepteur non disponible");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isRecepteur() {
		return recepteur;
	}

	public void setRecepteur(boolean recepteur) {
		this.recepteur = recepteur;
	}

	public MianController() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Si on doit effacer, on ne passe pas dans le else => pas de dessin
		if (this.erasing) {
			this.erasing = false;
		} else {
			// On parcourt notre collection de points
			for (VoPoint p : this.points) {
				// On r�cup�re la couleur
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

	public void erase() {
		if (recepteur == false) {
			this.erasing = true;
			this.points = Collections
					.synchronizedList(new ArrayList<VoPoint>());

			repaint();
			sendToSocket();
		}

	}

	private void sendToSocket() {

		Emission ee = new Emission(points, ipRecepteur);

		ee.sendToSocketEmission(socketEmission);

	}

	public void setPointerType(String str) {
		this.pointerType = str;
	}

	public void setPointerColor(Color c) {
		this.pointerColor = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (recepteur == false) {
			oldX = posX;
			oldY = posY;
			posX = e.getX();
			posY = e.getY();

			points.add(new VoPoint(e.getX() - (pointerSize / 2), e.getY()
					- (pointerSize / 2), pointerSize, pointerColor, pointerType));
			setPointsToRemove(); // on efface la liste de points a effacer
			pointsToRemove.add(new VoPoint(e.getX() - (pointerSize / 2), e
					.getY() - (pointerSize / 2), pointerSize, pointerColor,
					pointerType));

			repaint();
			sendToSocket();
		}

	}

	public List<VoPoint> getPoints() {
		return points;
	}

	public void setPoints(List<VoPoint> points) {
		this.points = points;
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

	@Override
	public void mouseDragged(MouseEvent e) {
		if (recepteur == false) {
			oldX = posX;
			oldY = posY;
			posX = e.getX();
			posY = e.getY();
			points.add(new VoPoint(e.getX() - (pointerSize / 2), e.getY()
					- (pointerSize / 2), pointerSize, pointerColor, pointerType));
			pointsToRemove.add(new VoPoint(e.getX() - (pointerSize / 2), e
					.getY() - (pointerSize / 2), pointerSize, pointerColor,
					pointerType));

			repaint();
			sendToSocket();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* Recuperation des points pour le plugin */
	public void setPointsToRemove() {
		this.pointsToRemove = Collections
				.synchronizedList(new ArrayList<VoPoint>());
	}

	public List<VoPoint> getPointsToRemoveorAdd() {
		return pointsToRemove;
	}

	public void setPointerSize(int pointerSize) {
		this.pointerSize = pointerSize;
	}

}
