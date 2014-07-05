package packclient;

import java.awt.Frame;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.JOptionPane;

public class Emission {

	private static ObjectOutputStream outObject = null;
	private List<VoPoint> points = null;
	private boolean islaunch = true;
	public InetAddress ipRecepteur = null;

	public Emission(List<VoPoint> points, InetAddress ipRecepteur) {
		this.points = points;
		this.ipRecepteur = ipRecepteur;

	}

	public boolean isIslaunch() {
		return islaunch;
	}

	public void setIslaunch(boolean islaunch) {
		this.islaunch = islaunch;
	}

	public synchronized void sendToSocketEmission(Socket socket2) {

		try {

			outObject = new ObjectOutputStream(socket2.getOutputStream());

			synchronized (points) {
				outObject.writeObject(points);
			}
			outObject.flush();

		} catch (SocketException e) {
			JOptionPane.showMessageDialog(new Frame(), socket2.getInetAddress() + " s'est deconnect�.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
