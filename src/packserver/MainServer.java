package packserver;

import javax.swing.SwingUtilities;

/**
 * Classe permattant le lancement de l'IHM
 * 
 * @author pascal
 * 
 */
public class MainServer {

	public static void main(String[] args) {

		Runnable r = new Runnable() {
			public void run() {
				new IhmServer();
			}
		};
		SwingUtilities.invokeLater(r);
	}

}
