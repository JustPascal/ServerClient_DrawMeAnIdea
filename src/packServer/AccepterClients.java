package packServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AccepterClients implements Runnable {

	private ServerSocket socketserver;
	private Socket socket;
	private int nbrclient = 1;
	private ArrayList<InetAddress> ipClients = null;

	public AccepterClients(ServerSocket s) {
		socketserver = s;
	}

	public void run() {

		try {
			while (true) {
				socket = socketserver.accept(); 
				System.out.println("Le client numéro " + nbrclient
						+ " est connecté !");
				System.out
						.println("Son adresse est " + socket.getInetAddress());
				ipClients = new ArrayList<InetAddress>();
				ipClients.add(socket.getInetAddress());
				nbrclient++;
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
