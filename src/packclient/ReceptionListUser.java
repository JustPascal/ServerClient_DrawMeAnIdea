package packclient;

import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JOptionPane;

public class ReceptionListUser implements Runnable {

	public Socket socket = null;
	private ObjectInputStream objectReceptionList;
	public List<InetAddress> ipClients;
	public JMenu inviter;
	public MainView mvView;

	public ReceptionListUser(Socket socket, MainView mvView) {
		this.socket = socket;
		this.mvView = mvView;

	}

	@Override
	@SuppressWarnings("unchecked")
	public void run() {

		ipClients = new ArrayList<InetAddress>();

		try {
			while (true) {
				objectReceptionList = new ObjectInputStream(socket.getInputStream());
				ipClients = (List<InetAddress>) objectReceptionList.readObject();
				this.setIpClients(ipClients);

				mvView.setIpClients(ipClients);
			}
		} catch (StreamCorruptedException e) {
			System.out.println("streamcorruptedexecption");
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(new Frame(), "Le serveur s'est eteint");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(List<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

}
