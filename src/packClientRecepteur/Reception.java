package packClientRecepteur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import modele.Point;
import editeur.DrawPanelReception;
import editeur.ViewRecepteur;

public class Reception implements Runnable {

	private DrawPanelReception drawPanel = null;
	private ObjectInputStream inObject;
	private ViewRecepteur mc2 = null;
	private Socket socket = null;

	/*
	 * public RecepteurReception(MainControleurRecepteur mc, ObjectInputStream
	 * inObject) { this.mc = mc; this.drawPanel =
	 * mc.getMainView().getDrawPanel(); this.inObject = inObject;
	 * 
	 * }
	 */

	public Reception(ViewRecepteur mc, Socket socket) {
		this.mc2 = mc;
		this.drawPanel = mc.getDrawPanel();
		this.socket = socket;

	}

	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		List<Point> alPoint;
		System.out.println("meme pas la ?");
		// while (true) {

		System.out.println(socket.toString());
		System.out.println(socket.isConnected());

		// Chat chat = new Chat(socket);
		// Thread t3 = new Thread(chat);
		// t3.start();
		// inObject = new ObjectInputStream(socket.getInputStream());

		System.out.println("alimentation du inObject");

		// System.out.println(inObject.toString());

		try {

			while (true) {
				System.out.println("ici ca passe");
				inObject = new ObjectInputStream(socket.getInputStream());
				System.out.println("ici ca passe pas");
				alPoint = (List<Point>) inObject.readObject();

				System.out.println("weshhh " + alPoint.toString());
				drawPanel.setPoints(alPoint);
				drawPanel.repaint();
			}

		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		// }
	}

	public ViewRecepteur getMc2() {
		return mc2;
	}
}
