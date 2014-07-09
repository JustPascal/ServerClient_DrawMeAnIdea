package packclient;

import java.awt.Frame;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Class Emission permet d'envoyer la collection point à
 * 
 * @author yossi
 * 
 */
public class Emission {

	private static ObjectOutputStream outObject = null;
	private List<VoPoint> points = null;
	/**
	 * Adresse Ip du récepteur
	 */
	public InetAddress ipRecepteur = null;

	/**
	 * Constructeur Emission
	 * 
	 * @param points
	 *            : Liste de points à envoyer à un utilisateur
	 * @param ipRecepteur
	 *            : Adresse Ip du récepteur qui recevra les points
	 */
	public Emission(List<VoPoint> points, InetAddress ipRecepteur) {
		this.points = points;
		this.ipRecepteur = ipRecepteur;

	}

	/**
	 * Serealize l'objet point et l'envoi par socket
	 * 
	 * @param socket
	 */
	public synchronized void sendToSocketEmission(Socket socket) {

		try {

			outObject = new ObjectOutputStream(socket.getOutputStream());

			synchronized (points) {
				outObject.writeObject(points);
			}
			outObject.flush();

		} catch (SocketException e) {
			JOptionPane.showMessageDialog(new Frame(), socket.getInetAddress()
					+ " s'est deconnect�.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
