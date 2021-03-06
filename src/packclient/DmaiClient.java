package packclient;

import java.awt.Frame;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Class permettant d'instancier la vue et la connexion au serveur.
 * 
 * @author yossi
 * 
 */
public class DmaiClient {

	/**
	 * Socket
	 */
	public static Socket socket = null;

	private static Logger logger = Logger.getLogger(DmaiClient.class.getName());

	MainView mainView;

	/**
	 * Constructeur instanciant la vue
	 */
	public DmaiClient() {
		this.mainView = new MainView();
		connexionServeur();

	}

	/**
	 * Ouverture d'un socket relier au serveur
	 */
	public void connexionServeur() {

		String serverIp = JOptionPane
				.showInputDialog("Entrez le nom du serveur.");
		try {
			socket = new Socket(serverIp, 4456);
			logger.info("Connexion au socket serveur.");

			Thread t = new Thread(new EnvoiPresence(socket));
			t.start();

			Thread t2 = new Thread(new ReceptionListUser(socket, mainView));
			t2.start();

		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(new Frame(),
					"Le serveur Draw Me An Idea n'est pas lancé", "Erreur", 1);
			System.exit(0);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
