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

/**
 * Class permettant à un utilisateur de recuperer la liste de tous les
 * utilisateurs qui s'est sont connecté aux serveur
 * 
 * @author yossi
 * 
 */
public class ReceptionListUser implements Runnable {

	public Socket socket = null;
	private ObjectInputStream objectReceptionList;
	public List<InetAddress> ipClients;
	public JMenu inviter;
	public MainView mvView;

	/**
	 * Constructer avec en parametre le Socket et le mainView
	 * 
	 * @param socket
	 * @param mvView
	 */
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
				objectReceptionList = new ObjectInputStream(
						socket.getInputStream());
				ipClients = (List<InetAddress>) objectReceptionList
						.readObject();
				this.setIpClients(ipClients);

				mvView.setIpClients(ipClients);
			}
		} catch (StreamCorruptedException e) {
			System.out.println("streamcorruptedexecption");
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(new Frame(),
					"Le serveur s'est eteint");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO Exception dans Reception List User.");
		} catch (ClassNotFoundException e) {
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
