package packClientEmetteur;

import java.io.ObjectOutputStream;
import java.util.List;

import modele.Point;

public class Emission {

	private static ObjectOutputStream outObject = null;
	private List<Point> points = null;
	private boolean islaunch = true;

	// private Socket socket = new Socket("127.0.0.1", 2009);

	public Emission(List<Point> points) {
		this.points = points;
	}

	public boolean isIslaunch() {
		return islaunch;
	}

	public void setIslaunch(boolean islaunch) {
		this.islaunch = islaunch;
	}

	public synchronized void sendToSocketEmission() {

		try {
			outObject = new ObjectOutputStream(ClientEmetteur.getSocket()
					.getOutputStream());
			System.out.println(points.toString());
			synchronized (points) {
				outObject.writeObject(points);
			}
			outObject.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
