package packServer;

import java.io.IOException;

import java.net.ServerSocket;

public class DmaiServer {

	public static ServerSocket ss = null;

	public static void main(String[] args) {

		try {
			System.out.println("Lancement du serveur..");
			ss = new ServerSocket(2009);
			System.out.println("Le serveur est à l'ecoute du port "
					+ ss.getLocalPort());

			Thread t = new Thread(new AccepterClients(ss));
			t.start();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
