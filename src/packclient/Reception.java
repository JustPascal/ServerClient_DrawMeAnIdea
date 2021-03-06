package packclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @author yossi
 * 
 */
public class Reception implements Runnable {

	private MianController mianController = null;
	private ObjectInputStream inObject;
	private MainView mv2 = null;
	private Socket socket = null;

	/**
	 * 
	 * @param mv2
	 * @param socketR
	 */
	public Reception(MainView mv2, Socket socketR) {
		this.mv2 = mv2;
		this.mianController = mv2.getDrawPanel();
		this.socket = socketR;

	}

	@Override
	@SuppressWarnings("unchecked")
	/**
	 * 
	 */
	public void run() {
		List<VoPoint> alPoint;

		System.out.println("alimentation du inObject");

		try {

			while (true) {

				inObject = new ObjectInputStream(socket.getInputStream());

				alPoint = (List<VoPoint>) inObject.readObject();

				mianController.setPoints(alPoint);
				mianController.repaint();
			}

		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null, socket.getInetAddress()
					+ " s'est deconnecté");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public MainView getv2() {
		return mv2;
	}
}
