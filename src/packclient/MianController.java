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

/**
 * MainController est un panel sur lequel un utilisateur fera son dessin
 * 
 * @author pascal et yossi
 * 
 */
public class MianController extends JPanel implements Serializable,
		MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2250985983378564880L;

	private String pointerType = "CIRCLE";

	private Color pointerColor = Color.red;

	private boolean effacerDessin = true;

	private int pointerSize = 15;

	private List<VoPoint> points = Collections
			.synchronizedList(new ArrayList<VoPoint>());

	/* Points to remove */
	private List<VoPoint> pointsToRemove = Collections
			.synchronizedList(new ArrayList<VoPoint>());
	/* -- -- -- -- -- -- */
	public boolean recepteur = true;

	public InetAddress ipRecepteur = null;

	public Socket socketEmission = null;

	/**
	 * Constructeur permettant d'initialiser le panel de dessin
	 * */
	public MianController() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (this.effacerDessin) {
			this.effacerDessin = false;
		} else {
			for (VoPoint p : this.points) {
				g.setColor(p.getColor());

				if (p.getType().equals("SQUARE")) {
					g.fillRect(p.getX(), p.getY(), p.getSize(), p.getSize());
				} else {
					g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
				}
			}
		}
	}

	/**
	 * fonction permettant d'effacer le contenu du panel dessin ainsi que les
	 * points qui sont envoyés vers un utilisateur récepteur
	 * */
	public void erase() {
		if (recepteur == false) {
			this.effacerDessin = true;
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

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (recepteur == false) {
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

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (recepteur == false) {
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

}
