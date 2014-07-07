package packserver;

import javax.swing.SwingUtilities;


public class MainServer {

	
	public static void main(String[] args) {
		
		 Runnable r = new Runnable(){
				public void run(){
					new IhmServer();
				}
			};
			SwingUtilities.invokeLater(r);
	}
	
	
}
