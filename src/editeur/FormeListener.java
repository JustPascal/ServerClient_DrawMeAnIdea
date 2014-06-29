package editeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FormeListener implements ActionListener {

	private DrawPanelEmetteur drawPanel;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass().getName().equals("javax.swing.JMenuItem")) {
			if (e.getSource() == ViewEmetteur.carre)
				drawPanel.setPointerType("SQUARE");
			else
				drawPanel.setPointerType("CIRCLE");
		} else {
			if (e.getSource() == ViewEmetteur.square)
				drawPanel.setPointerType("SQUARE");
			else
				drawPanel.setPointerType("CIRCLE");
		}
	}
}
