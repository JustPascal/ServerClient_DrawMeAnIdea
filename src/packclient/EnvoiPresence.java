package packclient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * Envoie La presence d'un utilisateur à un serveur
 * 
 * @author yossi
 * 
 */
public class EnvoiPresence implements Runnable {

	/**
	 * Socket
	 */
	public Socket socket = null;
	private ObjectOutputStream objectEnvoi;
	private InetAddress localAddress;

	/**
	 * Constructeur avec en paramètre le socket
	 * 
	 * @param socket
	 */
	public EnvoiPresence(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			localAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {

			try {
				objectEnvoi = new ObjectOutputStream(socket.getOutputStream());
				objectEnvoi.writeObject(localAddress);
				objectEnvoi.flush();

			} catch (IOException e) {
				JOptionPane
						.showMessageDialog(null,
								"Le serveur viens d'être desactiver.\n l'application va s'éteindre.");
				System.exit(0);

			}
		}

	}

}
