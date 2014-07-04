package packServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class DmaiServer {

	public static ServerSocket ss = null;

	public static void main(String[] args) {

		try {
			InetAddress thisIp = InetAddress.getLocalHost();

			System.out.println("Lancement du serveur..");
			ss = new ServerSocket(4456, 5, thisIp);
			System.out.println("Le serveur est à l'ecoute du port "
					+ ss.getLocalPort());

			Thread t = new Thread(new AccepterClients(ss));
			t.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
