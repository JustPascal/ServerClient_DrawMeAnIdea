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

	public static void main(String[] args) {

		try {

			socket = new Socket("192.168.0.21.", 4456);
			logger.info("Connexion au socket serveur.");
			MainView mvView;
			mvView = new MainView();

			Thread t = new Thread(new EnvoiPresence(socket));
			t.start();

			Thread t2 = new Thread(new ReceptionListUser(socket, mvView));
			t2.start();

		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(new Frame(),
					"Le serveur Draw Me An Idea n'est pas lanc�", "Erreur", 1);
			System.exit(0);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
