package packserver;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IhmServer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IhmServer(){
		setTitle("Serveur Draw me an idea !");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(new Dimension(300, 150));
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{56, 63, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblServeurDrawMe = new JLabel("Serveur Draw me an idée en cours d'execution.");
		GridBagConstraints gbc_lblServeurDrawMe = new GridBagConstraints();
		gbc_lblServeurDrawMe.insets = new Insets(0, 0, 5, 0);
		gbc_lblServeurDrawMe.gridx = 0;
		gbc_lblServeurDrawMe.gridy = 0;
		panel.add(lblServeurDrawMe, gbc_lblServeurDrawMe);
		
		JButton btnEteindre = new JButton("Eteindre ");
		btnEteindre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnEteindre = new GridBagConstraints();
		gbc_btnEteindre.gridx = 0;
		gbc_btnEteindre.gridy = 1;
		panel.add(btnEteindre, gbc_btnEteindre);
		
		
	
		setVisible(true);
	}
	
}
