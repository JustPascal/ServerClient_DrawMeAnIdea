package packclient;

import javax.swing.SwingUtilities;

/**
 * Main permettant de lancer l'IHM de l'application Draw Me An Idea
 * 
 * @author pascal
 * 
 */
public class MainClient {

	/**
	 * Fonction Main pour l'application Draw Me An Idea
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Runnable r = new Runnable() {
			public void run() {
				new DmaiClient();
			}
		};
		SwingUtilities.invokeLater(r);
	}

}
