package packClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EnvoiPresence implements Runnable {

	public Socket socket = null;
	private ObjectOutputStream objectEnvoi;
	private InetAddress localAddress;

	public EnvoiPresence(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			localAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {

			try {
				objectEnvoi = new ObjectOutputStream(socket.getOutputStream());
				objectEnvoi.writeObject(localAddress);
				objectEnvoi.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
