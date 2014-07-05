package packserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class AccepterClients implements Runnable {

	private final ServerSocket socketserver;
	private Socket socket;
	private int nbrclient = 1;
	private ArrayList<InetAddress> ipClients;

	public ArrayList<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(ArrayList<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

	public AccepterClients(ServerSocket s) {
		socketserver = s;
	}

	@Override
	public void run() {

		try {
			ipClients = new ArrayList<InetAddress>();

			while (true) {
				socket = socketserver.accept();

				System.out.println("Le client numéro " + nbrclient + " est connecté !");

				nbrclient++;
				Thread t = new Thread(new ReceptionPresence(socket, this));
				t.start();

				// Thread t2 = new Thread(new EnvoiList(socket, ipClients));
				// t2.start();

			}

		} catch (SocketException e) {
			System.out.println("le client " + socket.getInetAddress() + " s'est d�connect�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
