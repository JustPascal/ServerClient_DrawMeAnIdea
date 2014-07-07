package packclient;

import javax.swing.SwingUtilities;


public class MainClient {

	public static void main(String[] args) {
		
		 Runnable r = new Runnable(){
				public void run(){
					new DmaiClient();
				}
			};
			SwingUtilities.invokeLater(r);
	}
	
	
}
