package testClients;

import java.net.Socket;

public class Client1 {

	public static Socket socket = null;

	public static void main(String[] args) throws Exception {

		try {

			socket = new Socket("127.0.0.1", 2009);

		} catch (Exception e) {

		}

	}

}
