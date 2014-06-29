package editeur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CouleurListener implements ActionListener {

	private DrawPanelEmetteur drawPanel;

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(e.getSource().getClass().getName());

		if (e.getSource().getClass().getName().equals("javax.swing.JMenuItem")) {

			System.out.println("OK !");

			if (e.getSource() == ViewEmetteur.vert)
				drawPanel.setPointerColor(Color.green);

			else if (e.getSource() == ViewEmetteur.bleu)
				drawPanel.setPointerColor(Color.blue);

			else
				drawPanel.setPointerColor(Color.red);
		} else {

			if (e.getSource() == ViewEmetteur.green)
				drawPanel.setPointerColor(Color.green);

			else if (e.getSource() == ViewEmetteur.blue)
				drawPanel.setPointerColor(Color.blue);

			else
				drawPanel.setPointerColor(Color.red);
		}
	}
}