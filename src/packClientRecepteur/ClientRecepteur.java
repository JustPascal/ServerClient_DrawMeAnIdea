package packClientRecepteur;

import java.net.ServerSocket;
import java.net.Socket;

import editeur.ViewRecepteur;

public class ClientRecepteur {

	public static ServerSocket ss = null;
	private static Socket socket = null;
	public static Thread t, t2;

	public static void main(String[] args) {

		try {

			ViewRecepteur mainView2 = new ViewRecepteur();
			// ControllerRecepteur mcmain = new ControllerRecepteur();
			// t2 = new Thread(mcmain);

			// t2.start();

			ss = new ServerSocket(2009);
			System.out.println("Le serveur est à l'écoute du port "
					+ ss.getLocalPort());

			socket = ss.accept();
			System.out.println("client connecté au recepteur");

			// RecepteurReception rrReception = new RecepteurReception(mcmain,
			// socket);
			Reception rrReception = new Reception(mainView2, socket);
			new Thread(rrReception).start();
			// t = new Thread(new RecepteurReception(mcmain, socket));
			// t.start();

		} catch (Exception e) {
			System.err.println("Le port " + ss.getLocalPort()
					+ " est déjà utilisé !");
		}

	}
}
