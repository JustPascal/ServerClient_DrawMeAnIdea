package packServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ReceptionPresence implements Runnable {

	public Socket socket;
	private ObjectInputStream objectReceptionList;
	private InetAddress clientAddress = null;
	// public ArrayList<InetAddress> ipClients = null;
	private AccepterClients accepterClients = null;

	private static ObjectOutputStream outObjectList = null;

	public ReceptionPresence(Socket socket, AccepterClients accepterClients) {
		this.socket = socket;
		this.accepterClients = accepterClients;

	}

	@Override
	public void run() {
		// ipClients = new ArrayList<InetAddress>();
		try {
			while (true) {
				objectReceptionList = new ObjectInputStream(
						socket.getInputStream());
				clientAddress = (InetAddress) objectReceptionList.readObject();

				if (!accepterClients.getIpClients().contains(clientAddress)) {
					accepterClients.getIpClients().add(clientAddress);
				}
				// System.out.println(accepterClients.getIpClients());

				envoilist(accepterClients.getIpClients());
			}
		} catch (SocketException e) {
			System.out.println("le client s'est deconnecté");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void envoilist(ArrayList<InetAddress> ipClients2) {
		try {
			while (true) {
				outObjectList = new ObjectOutputStream(socket.getOutputStream());
				outObjectList.writeObject(ipClients2);

				outObjectList.flush();
			}
		} catch (SocketException e) {
			System.out.println("un client s'est deconnect�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
