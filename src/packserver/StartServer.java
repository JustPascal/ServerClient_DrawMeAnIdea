package packserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Classe permettant de démarrer le serveur le serveur
 * 
 * @author pascal
 * 
 */
public class StartServer {

	private ServerSocket ss = null;

	/**
	 * Constructeur qui demarre le serveur sur un IP définie
	 * 
	 * @param thisIp
	 */
	public StartServer(InetAddress thisIp) {
		try {
			System.out.println("Lancement du serveur..");
			this.ss = new ServerSocket(4456, 5, thisIp);
			System.out.println("Le serveur est à l'ecoute du port "
					+ this.ss.getLocalPort());

			Thread t = new Thread(new AccepterClients(ss));
			t.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
