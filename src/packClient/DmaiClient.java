package packClient;

import java.awt.Frame;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class DmaiClient {

	public static Socket socket = null;
	private static Logger logger = Logger.getLogger(DmaiClient.class.getName());
	private ArrayList<InetAddress> ipClients;

	public ArrayList<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(ArrayList<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

	public static void main(String[] args) {

		try {

			socket = new Socket("10.242.142.168", 4456); // Serveur : Poste de
															// Yossi
			// socket = new Socket("10.242.142.1", 4456); // Serveur : Poste de
			// Pascal
			logger.info("Connexion au socket serveur.");
			MainView mvView;
			mvView = new MainView();

			Thread t = new Thread(new EnvoiPresence(socket));
			t.start();

			Thread t2 = new Thread(new ReceptionListUser(socket, mvView));
			t2.start();

		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(new Frame(), "Le serveur Draw Me An Idea n'est pas lancé", "Erreur", 1);
			System.exit(0);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
