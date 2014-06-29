package packClientEmetteur;

import java.net.InetAddress;
import java.net.Socket;

import editeur.ViewEmetteur;

public class ClientEmetteur {

	public static Socket socket = null;

	public static void main(String[] args) throws Exception {

		try {
			InetAddress clientadresse = InetAddress.getLocalHost();
			//System.out.println("client adresse : "+clientadresse.getHostAddress());
			socket = new Socket("127.0.0.1", 2009,clientadresse,2008);
			//socket = new Socket("127.0.0.1", 2009, );
			
			new ViewEmetteur();

			System.out.println("demande de connexion au clientRecepteur");

			// points = mcmain.getMainView().getDrawPanel().getPoints();
			// ChatEmetteurVersRecepteur cevr = new ChatEmetteurVersRecepteur(
			// socket, points);

			// t1 = new Thread(cevr);
			// t1.start();

		} catch (Exception e) {

		}
		/*
		 * catch (UnknownHostException e) {
		 * System.err.println("impossible de se connecter au clientRecepteur");
		 * 
		 * } catch (IOException e) {
		 * System.err.println("aucun clientRecepteur � l'ecoute du port" +
		 * socket.getLocalPort()); }
		 */
	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		ClientEmetteur.socket = socket;
	}

}
